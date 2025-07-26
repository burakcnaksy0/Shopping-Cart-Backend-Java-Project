package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends AbstractExceptionHandler {
    public ProductNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
