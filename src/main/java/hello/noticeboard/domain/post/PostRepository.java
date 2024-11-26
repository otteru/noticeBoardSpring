package hello.noticeboard.domain.post;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepository {

    private static final Map<Long, Post> store = new HashMap<>();

    private long sequence = 0L;

    public Post save(Post post) {
        post.setId(++sequence);;
        store.put(post.getId(), post);
        return post;
    }

    public void deleteById(long id) {
        store.remove(id);
    }

    public void update(Long postId ,Post updateParam) {
        //HashMap에서 postId에 해당하는 Post 객체의 참조를 반환하기에 Map 안의 Value가 수정이 된다.
        Post post = store.get(postId);
        post.setTitle(updateParam.getTitle());
        post.setBody(updateParam.getBody());
    }

    public Post findById(long id) {
        return store.get(id);
    }

    public List<Post> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
