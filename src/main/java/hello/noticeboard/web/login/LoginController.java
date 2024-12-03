package hello.noticeboard.web.login;

import hello.noticeboard.domain.login.LoginService;
import hello.noticeboard.domain.member.Member;
import hello.noticeboard.web.SessionConst;
import hello.noticeboard.web.validation.ErrorResult;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final MessageSource messageSource;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm loginForm) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

        log.info("form={}", form);

        if(bindingResult.hasErrors()) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            log.info("errorResult1={}", errorResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if(loginMember == null) {
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            log.info("errorResult2={}", errorResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        // HttpSession에 true(default 값)를 넣으면 세션 생성 flase를 넣으면 기존에 존재하던 세션 반환
        HttpSession session = request.getSession();
        // 서버 sessionStore에 session을 등록
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

        return ResponseEntity.ok().body(Map.of("success", true));
    }

    //AJAX를 사용하여 로그아웃을 구현할 수도 있지만, 현재 구현에 비해 특별한 이점이 없다.
    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            // 서버에서 세션을 삭제
            session.invalidate();
        }
        return "redirect:/";

    }

    private static void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
