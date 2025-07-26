package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends AbstractExceptionHandler {
    public OrderNotFoundException(String msg) {
        super(msg, HttpStatus.NOT_FOUND);
    }
}
