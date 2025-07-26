package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class CartNotFoundException extends AbstractExceptionHandler{
    public CartNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
