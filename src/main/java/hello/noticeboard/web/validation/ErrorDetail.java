package hello.noticeboard.web.validation;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Locale;

@Getter
@Slf4j
public class ErrorDetail {

    private String objectName;
    private String field;
    private String code;
    private String message;

    // 그냥 @Builder 이렇게 넣으면 충돌나서 이름을 붙여줘야 한다.
    // 두개의 생성자가 동일한 클래스 내에 있으면 Lombok이 하나의 빌더 클래스에만 생성하기에 클래스를 나눠줘야 한다.

    @Builder(builderMethodName = "objectErrorBuilder", builderClassName = "ObjectErrorBuilder")
    public ErrorDetail(ObjectError objectError, MessageSource messageSource, Locale locale) {
//        log.info("objectErrorBuilder");
        this.objectName = objectError.getObjectName();
        this.field = null;
        this.code = objectError.getCode();
        this.message = messageSource.getMessage(objectError, locale);
    }

    @Builder(builderMethodName = "fieldErrorBuilder", builderClassName = "FieldErrorBuilder")
    public ErrorDetail(FieldError fieldError, MessageSource messageSource, Locale locale) {
//        log.info("fieldErrorBuilder");
        this.objectName = fieldError.getObjectName();
        this.field = fieldError.getField();
        this.code = fieldError.getCode();
        this.message = messageSource.getMessage(fieldError, locale);
    }

}
