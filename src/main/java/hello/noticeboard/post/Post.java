package hello.noticeboard.post;

import lombok.Data;

@Data
public class Post {

    private Long postId;
    private String title;
    private String body;
}
