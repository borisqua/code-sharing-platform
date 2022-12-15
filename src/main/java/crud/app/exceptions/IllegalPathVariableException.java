package crud.app.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// todo >> this exception is here just for reference. It is being used nowhere.
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Illegal path variable")
public class IllegalPathVariableException extends RuntimeException {
    IllegalPathVariableException(String message) {
        super(message);
    }
}
