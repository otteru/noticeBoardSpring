package hello.noticeboard.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller// 컨트롤러로 등록해준다.
public class MyController {

    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
