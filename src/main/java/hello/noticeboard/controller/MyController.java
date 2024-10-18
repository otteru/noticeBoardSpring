package hello.noticeboard.controller;

import hello.noticeboard.post.Post;
import hello.noticeboard.post.PostRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/write")
    public String write() {
        return "write";
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
