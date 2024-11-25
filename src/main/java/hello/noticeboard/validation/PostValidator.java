package hello.noticeboard.validation;

import hello.noticeboard.post.Post;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PostValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Post.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Post post = (Post) target;

        //검증 로직
        if(!StringUtils.hasText(post.getTitle())) {
            errors.rejectValue("title", "NotBlank");
        }
        if(post.getBody() == null || post.getBody().length() < 5 || post.getBody().length() > 400) {
            errors.rejectValue("body", "Range", new Object[]{5, 400}, null);
        }

        // 아직 복합 룰 검증할 건 없는 듯?

    }
}
