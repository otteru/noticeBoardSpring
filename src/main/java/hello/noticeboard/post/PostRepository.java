package hello.noticeboard.post;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class PostRepository {

    private static final Map<Long, String> store = new HashMap<>();
}
