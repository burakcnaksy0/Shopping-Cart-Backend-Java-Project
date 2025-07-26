package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class AlreadyExistsCategoryException extends AbstractExceptionHandler {
    public AlreadyExistsCategoryException(String s) {
        super(s, HttpStatus.CONFLICT);
    }
}
