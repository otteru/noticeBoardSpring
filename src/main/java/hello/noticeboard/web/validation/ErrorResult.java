package hello.noticeboard.web.validation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;

/**
 * HTTP body에 들어갈 객체
 * getter를 사용해서 JSON문자열로 변경???
 */
@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResult {

    private List<ErrorDetail> errorDetails = null;

    @Builder
    public ErrorResult(Errors errors, MessageSource messageSource, Locale locale) {

        if(errors.hasFieldErrors()) {
//            log.info("field errors");
            this.errorDetails = errors.getFieldErrors()
                    .stream()
                    .map(error ->
                            ErrorDetail.fieldErrorBuilder()
                                    .fieldError(error)
                                    .messageSource(messageSource)
                                    .locale(locale)
                                    .build()
                    ).toList();
        }

        if(errors.hasGlobalErrors()) {
//            log.info("global errors");
            this.errorDetails = errors.getGlobalErrors()
                    .stream()
                    .map(error -> ErrorDetail.objectErrorBuilder()
                            .objectError(error)
                            .messageSource(messageSource)
                            .locale(locale)
                            .build()
                    ).toList();
        }

    }
}
