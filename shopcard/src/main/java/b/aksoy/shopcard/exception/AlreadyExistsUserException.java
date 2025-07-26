package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class AlreadyExistsUserException extends AbstractExceptionHandler {
    public AlreadyExistsUserException(String message) {
        super(message, HttpStatus.CONFLICT);
    }
}
