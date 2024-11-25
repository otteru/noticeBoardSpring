package hello.noticeboard;

import hello.noticeboard.post.Post;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BeanValidationTest {

    @Test
    void beanValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Post post = new Post();
        post.setTitle(" ");
        post.setBody("asdfaasdfasfsadfsafsa");

        Set<ConstraintViolation<Post>> violations = validator.validate(post);
        for (ConstraintViolation<Post> violation : violations) {
            System.out.println("violation = " + violation);
            System.out.println("violation = " + violation.getMessage());
        }
    }
}
