package hello.noticeboard.controller;

import hello.noticeboard.post.Post;
import hello.noticeboard.post.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller// 컨트롤러로 등록해준다.
@RequestMapping("/noticeBoard/posts")
@RequiredArgsConstructor //final 필드, @NonNull 필드 생성자 자동 생성
public class MyController {

    private final PostRepository postRepository;

    @GetMapping
    public String home(Model model) {
        List<Post> posts = postRepository.findAll();
        model.addAttribute("posts", posts);
        return "posts";
    }
    
    @GetMapping("/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        Post post = postRepository.findById(id);
        model.addAttribute("post", post);
        return "post";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("post", new Post());
        return "addForm";
    }

    @PostMapping("/add")
    public String addPost(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {

        log.info("Post={}", post);

        Post savedPost = postRepository.save(post);
        redirectAttributes.addAttribute("postId", savedPost.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/noticeBoard/posts/{postId}";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        postRepository.deleteById(id);
        return ResponseEntity.ok().build();
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
