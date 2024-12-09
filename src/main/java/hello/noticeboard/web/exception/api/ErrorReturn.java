package hello.noticeboard.web.exception.api;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorReturn {
    private String code;
    private String message;
}
