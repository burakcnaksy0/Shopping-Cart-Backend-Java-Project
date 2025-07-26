package b.aksoy.shopcard.exception;

import b.aksoy.shopcard.exception.handler.AbstractExceptionHandler;
import org.springframework.http.HttpStatus;

public class ImageNotFoundException extends AbstractExceptionHandler {
    public ImageNotFoundException(String s) {
        super(s, HttpStatus.NOT_FOUND);
    }
}
