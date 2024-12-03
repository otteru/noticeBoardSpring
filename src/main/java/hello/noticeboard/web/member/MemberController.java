package hello.noticeboard.web.member;

import hello.noticeboard.domain.member.Member;
import hello.noticeboard.domain.member.MemberRepository;
import hello.noticeboard.web.validation.ErrorResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberRepository memberRepository;
    private final MessageSource messageSource;

    @GetMapping("/add")
    public String addForm(@ModelAttribute("member") Member member) {
        return "members/addMemberForm";
    }

    @PostMapping("/add")
    public ResponseEntity<?> save(@Validated @RequestBody Member member, BindingResult bindingResult){
        log.info("member={}", member);

        if(bindingResult.hasErrors()){
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());
            log.info("bindingResult={}", bindingResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        memberRepository.save(member);

        return ResponseEntity.ok().body(Map.of("success", true));
    }
}
