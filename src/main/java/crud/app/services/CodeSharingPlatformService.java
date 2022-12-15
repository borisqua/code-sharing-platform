package crud.app.services;

import crud.app.jpa.entities.SnippetEntity;
import crud.app.jpa.repositories.SnippetsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

@Service
public class CodeSharingPlatformService {
    
    private final SnippetsRepository snippets;
    
    @Autowired
    public CodeSharingPlatformService(SnippetsRepository snippets) {
        this.snippets = snippets;
    }
    
    //@Transactional // note that @Transactional is container managed transactions level (CMT)
    // it works only for unchecked exceptions by default, checked exception won't cause transaction rollback
    // to customize this behaviour use @Transaction(rollbackFor = Exception.class)
    public SharedCode getSharedCode(UUID id, Model model) {
        
        Optional<SnippetEntity> code = snippets.findById(id);
        
        if (code.isPresent()) {
            
            SnippetEntity snippetEntity = code.get();
            SharedCode sharedCode = new SharedCode(snippetEntity);
            
            LocalDateTime creationTime = sharedCode.getDate();
            
            boolean lifeTimeExpired = false;
            boolean showViews = false;
            boolean showTimeLimit = false;
            
            int lifeTime = sharedCode.getTime();
            if (lifeTime > 0) {
                showTimeLimit = true;
                LocalDateTime expirationTime = creationTime.plusSeconds(lifeTime);
                int timeLeft = (int) LocalDateTime.now().until(expirationTime, ChronoUnit.SECONDS);
                if (timeLeft <= 0) {
                    snippets.delete(snippetEntity);
                    sharedCode.setViews(0);
                    sharedCode.setTime(0);
                    lifeTimeExpired = true;
                } else {
                    sharedCode.setTime(timeLeft);
                }
            }
            
            int viewsRemained = sharedCode.getViews();
            if (viewsRemained == 1) {
                showViews = true;
                snippets.delete(snippetEntity);
                sharedCode.setViews(0);
                sharedCode.setTime(0);
            } else if (viewsRemained > 1) {
                showViews = true;
                viewsRemained--;
                sharedCode.setViews(viewsRemained);
                snippetEntity.setViewsLimit(viewsRemained);
                snippets.save(snippetEntity);
            }
            
            if(model != null) {
                if (showViews ) {
                    model.addAttribute("showViews", true);
                    model.addAttribute("views", sharedCode.getViews());
                }
                if (showTimeLimit) {
                    model.addAttribute("showTimelimit", true);
                    model.addAttribute("time", sharedCode.getTime());
                }
            }
            if (!lifeTimeExpired) {
                return sharedCode;
            }
            
        }
    
        return null;
    
    }
    
    public Page<SharedCode> getLastPage(int pageSize) {
        return snippets.findAllByTimeLimitAndViewsLimit(0, 0, PageRequest.of(0, pageSize, Sort.by("date").descending()))
            .map(SharedCode::new);
    }
    
    public SharedCodeCreationResponse saveSnippetEntity(SharedCode sharedCode) {
        SnippetEntity code = new SnippetEntity(sharedCode);
        snippets.save(code);
        UUID id = code.getId();
        return new SharedCodeCreationResponse(id);
    }
}
