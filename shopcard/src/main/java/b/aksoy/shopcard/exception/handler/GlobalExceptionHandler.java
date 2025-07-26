package b.aksoy.shopcard.exception.handler;

import b.aksoy.shopcard.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex) {
        String message = "You do not have permission to access this resource";
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(message)
                .cause(ex.getCause() != null ? ex.getCause().toString() : null)
                .build();
        return ResponseEntity.status(403).body(errorResponse);
    }
    @ExceptionHandler(AbstractExceptionHandler.class)
    public ResponseEntity<ErrorResponse>  handleAbstractException(AbstractExceptionHandler ex) {
        ErrorResponse errorResponse = ErrorResponse
                .builder()
                .message(ex.getMessage())
                .cause(ex.getCause() != null ? ex.getCause().toString() : null)
                        .build();
        return ResponseEntity.status(ex.getStatus()).body(errorResponse);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .cause(ex.getCause() != null ? ex.getCause().toString() : null)
                .build();
        return ResponseEntity.status(500).body(errorResponse);
    }

}
