package b.aksoy.shopcard.exception.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AbstractExceptionHandler extends RuntimeException {
    private HttpStatus status;
    public AbstractExceptionHandler(String message, HttpStatus status){
        super((message));
        this.status=status;
    }


}
