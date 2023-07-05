package crud.app.controllers;

import crud.app.services.CodeSharingPlatformService;
import crud.app.services.SharedCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

//@RepositoryRestController
@Controller
public class CodeSharingPlatformControllerHtml {
    
    private static final String ERROR_MESSAGE_ILLEGAL_ID = "Illegal id entered";
    
    private final CodeSharingPlatformService service;
    
    @Autowired
    public CodeSharingPlatformControllerHtml(CodeSharingPlatformService service) {
        this.service = service;
    }
    
    @RequestMapping(method = RequestMethod.GET, path = "/", produces = "text/html")
    public String homePage() {
        return "home";
    }
    
    @GetMapping(path = "/code/new", produces = "text/html")
    public String getNewCodeForm() {
        return "newcode";
    }
    
    @GetMapping(path = "/code/{Id}", produces = "text/html")
    public String getCodeHtml(@PathVariable(name = "Id") String uuid, Model model) {
        
        SharedCode sharedCode = service.getSharedCode(UUID.fromString(uuid), model);
        if (sharedCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_MESSAGE_ILLEGAL_ID);
//            throw new IllegalArgumentException(ERROR_MESSAGE_ILLEGAL_ID);
//            throw new IllegalPathVariableException(ERROR_MESSAGE_ILLEGAL_ID);
        }
        model.addAttribute("snippets", new SharedCode[]{sharedCode});
        model.addAttribute("title", "Code");
        return "code";
        
    }
    
    @GetMapping(path = "/code/latest", produces = "text/html")
    public String getLast10SnippetsHtml(Model model) {
        model.addAttribute("snippets", service.getLastPage(10));
        model.addAttribute("title", "Latest");
        return "code";
    }
    
}
