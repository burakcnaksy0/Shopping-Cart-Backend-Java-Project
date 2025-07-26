package b.aksoy.shopcard.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Builder
public class ErrorResponse {
    private String message;
    private String cause;
    private final LocalDateTime timestamp=LocalDateTime.now();
}
