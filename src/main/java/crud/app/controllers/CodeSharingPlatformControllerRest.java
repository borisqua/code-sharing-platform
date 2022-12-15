package crud.app.controllers;

import crud.app.services.CodeSharingPlatformService;
import crud.app.services.SharedCode;
import crud.app.services.SharedCodeCreationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

//@RepositoryRestController
@Controller
public class CodeSharingPlatformControllerRest {
    
    private static final String ERROR_MESSAGE_ILLEGAL_ID = "Illegal id entered";
    
    private final CodeSharingPlatformService service;
    
    @Autowired
    public CodeSharingPlatformControllerRest(CodeSharingPlatformService service) {
        this.service = service;
    }
    
    @PostMapping(path = "/api/code/new", consumes = "application/json", produces = /*MediaType.APPLICATION_JSON_VALUE*/"application/json")
    @ResponseBody
    public SharedCodeCreationResponse postNewCode(@RequestBody SharedCode newSharedCode) {
        // todo>> could' redirect to /code/{id} with current id
        return service.saveSnippetEntity(newSharedCode);
    }
    
    //@Transactional // note that @Transactional is container managed transactions level (CMT)
    // it works only for unchecked exceptions by default, checked exception won't cause transaction rollback
    // to customize this behaviour use @Transaction(rollbackFor = Exception.class)
    @GetMapping(path = "/api/code/{Id}", produces = "application/json")
    @ResponseBody
    public SharedCode getCodeJson(@PathVariable(name = "Id") UUID uuid) {
        SharedCode sharedCode = service.getSharedCode(uuid, null);
        if (sharedCode == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ERROR_MESSAGE_ILLEGAL_ID);
//            throw new IllegalArgumentException(ERROR_MESSAGE_ILLEGAL_ID);
//            throw new IllegalPathVariableException(ERROR_MESSAGE_ILLEGAL_ID);
        }
        return sharedCode;
    }
    
    @GetMapping(path = "/api/code/latest", produces = "application/json")
    @ResponseBody
    public SharedCode[] getLast10SnippetsJson() {
        return service.getLastPage(10).toList().toArray(new SharedCode[0]);
    }
    
}
