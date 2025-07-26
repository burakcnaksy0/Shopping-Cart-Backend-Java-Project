package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class CartItemNotFoundException extends AbstractExceptionHandler {
    public CartItemNotFoundException(String msg) {
        super(msg, HttpStatus.CONFLICT);
    }
}
