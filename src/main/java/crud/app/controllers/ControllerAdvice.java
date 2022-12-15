package crud.app.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@RestControllerAdvice
public class ControllerAdvice {
    
    private static final String ERROR_MESSAGE_ILLEGAL_ID = "Illegal id entered";
    private static final String ERROR_MESSAGE_SERVER_RUNTIME = "Server runtime error";
    
    @ExceptionHandler({IllegalArgumentException.class, ResponseStatusException.class})
    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Illegal id entered") //will override the exception handler return
    public HashMap<String, String> IllegalArgumentException(Exception e) { // or private
        HashMap<String, String> response = new HashMap<>();
        response.put("message", ERROR_MESSAGE_ILLEGAL_ID);
        response.put("error", e.getClass().getName());
        return response;
    }
    
    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "") //will override the exception handler return
    public HashMap<String, String> responseStatusException(Exception e) { // or private
        HashMap<String, String> response = new HashMap<>();
        response.put("message", ERROR_MESSAGE_SERVER_RUNTIME);
        response.put("error", e.getClass().getName());
        return response;
    }
    
}
