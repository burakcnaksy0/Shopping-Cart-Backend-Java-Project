package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends AbstractExceptionHandler {
    public CategoryNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
