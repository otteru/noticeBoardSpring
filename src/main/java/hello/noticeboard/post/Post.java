package hello.noticeboard.post;

import lombok.Data;

@Data
public class Post {

    private Long id;
    private String title;
    private String body;

    public Post(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Post() {

    }
}
