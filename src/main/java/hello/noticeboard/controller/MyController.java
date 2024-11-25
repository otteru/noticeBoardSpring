package hello.noticeboard.controller;

import hello.noticeboard.validation.ErrorResult;
import hello.noticeboard.validation.PostValidator;
import hello.noticeboard.post.Post;
import hello.noticeboard.post.PostRepository;
import hello.noticeboard.validation.form.PostEditForm;
import hello.noticeboard.validation.form.PostSaveForm;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@Slf4j
@Controller// 컨트롤러로 등록해준다.
@RequestMapping("/noticeBoard/posts")
@RequiredArgsConstructor //final 필드, @NonNull 필드 생성자 자동 생성
public class MyController {

    private final PostRepository postRepository;
    //private final PostValidator postValidator;
    private final MessageSource messageSource;

    //@InitBinder
    //public void initBinder(WebDataBinder dataBinder) {
    //    dataBinder.addValidators(postValidator);
    //}

    @GetMapping
    public String home(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }

    // read
    @GetMapping("/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        Post post = postRepository.findById(id);

        log.info("Read Post={}", post);

        model.addAttribute("post", post);
        return "post";
    }

    //  add
    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("post", new Post());
        return "addForm";
    }

    @PostMapping
    public ResponseEntity<?> addPost(@Validated @RequestBody PostSaveForm form, BindingResult bindingResult) {

        log.info("add Post with title={}", form.getTitle());

        // 검증에 실패하면 다시 입력 폼으로 back
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());

            log.info("errorResult={}", errorResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        Post post = new Post();
        post.setTitle(form.getTitle());
        post.setBody(form.getBody());
        Post savedPost = postRepository.save(post);
        return ResponseEntity.ok().body(Map.of("id", savedPost.getId()));
    }

    // edit
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable("id") Long id, Model model) {

        log.info("edit id={}", id);

        Post post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "editForm";
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> editPost(@Validated @RequestBody PostEditForm form, BindingResult bindingResult, @PathVariable("id") Long id) {

        log.info("form={}", form);

        Post existingPost = postRepository.findById(id);
        if (existingPost == null) {
            return ResponseEntity.notFound().build();
        }

        // 검증에 실패하면 다시 입력 폼으로 back
        if(bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            ErrorResult errorResult = new ErrorResult(bindingResult, messageSource, Locale.getDefault());


            log.info("errorResult={}", errorResult);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResult);
        }

        log.info("Updating post with id={}, title={}", id, form.getTitle());

        Post post = new Post();
        post.setId(form.getId());
        post.setTitle(form.getTitle());
        post.setBody(form.getBody());
        postRepository.update(id, post);
        return ResponseEntity.ok().body(Map.of("success", true));
    }


    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
        postRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // test data
    @PostConstruct
    public void init() {
        postRepository.save(new Post("Silver Linings Playbook", "The only way you can beat my crazy was by doing something crazy yourself. " +
                "Thank you. I love you. I knew it the minute I met you. I'm sorry it took so long for me to catch up. I just got stuck."));
        postRepository.save(new Post("Intern", "Ben: \"You're never wrong to do the right thing.\" " +
                "Jules: Who said that, you? Ben: Yeah. But I'm pretty sure Mark Twain said it first."));
    }
}
