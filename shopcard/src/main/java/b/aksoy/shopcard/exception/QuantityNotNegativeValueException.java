package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class QuantityNotNegativeValueException extends AbstractExceptionHandler {
    public QuantityNotNegativeValueException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
