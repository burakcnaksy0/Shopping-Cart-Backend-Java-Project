package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends AbstractExceptionHandler {
    public UserNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
