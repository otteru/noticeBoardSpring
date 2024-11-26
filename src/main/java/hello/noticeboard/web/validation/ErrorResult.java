package hello.noticeboard.web.validation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;

/**
 * HTTP body에 들어갈 객체
 * getter를 사용해서 JSON문자열로 변경???
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResult {

    private List<ErrorDetail> errorDetails;

    @Builder
    public ErrorResult(Errors errors, MessageSource messageSource, Locale locale) {
        this.errorDetails = errors.getFieldErrors()
                .stream()
                .map(error ->
                        ErrorDetail.builder()
                                .fieldError(error)
                                .messageSource(messageSource)
                                .locale(locale)
                                .build()
                ).toList();
    }
}
