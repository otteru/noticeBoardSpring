package hello.noticeboard;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

@SpringBootTest
public class ErrorMessageTest {

    @Autowired
    MessageSource ms;

    @Test
    void errorMessageTest() {
        String result = ms.getMessage("required", null, null);
    }
}
