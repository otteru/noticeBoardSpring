package hello.noticeboard.web.exception.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ErrorReturn RuntimeExceptionHandler(RuntimeException e) {
        log.error("[exceptionHandler] ex", e);
        return new ErrorReturn("POST_NOT_FOUND", e.getMessage());
    }
}
