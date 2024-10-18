package hello.noticeboard.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class PostRepositoryTest {

    PostRepository postRepository = new PostRepository();

    @AfterEach
    void afterEach() {
        postRepository.clearStore();
    }

    @Test
    void savePost() {
        // given
        Post post = new Post("About Time", " We're all traveling through time together, e" +
                "very day of our lives. All we can do is do our best to relish this remarkable ride.");

        // when
        Post savedPost = postRepository.save(post);

        // then
        Post findPost = postRepository.findById(savedPost.getId());
        assertThat(savedPost).isEqualTo(findPost);

    }

    @Test
    void updatePost() {
        // given
        Post post = new Post("About Time", " We're all traveling through time together, e" +
                "very day of our lives. All we can do is do our best to relish this remarkable ride.");
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        // when
        Post updateParam = new Post("About Time", "Maybe, just maybe, I'm the faller. Every family has someone who falls, " +
                "who doesn't make the grade, who stumbles, who life trips up. Maybe I'm our faller.\n" + "\n");
        postRepository.update(postId, updateParam);

        //then
        Post findPost = postRepository.findById(postId);
        assertThat(findPost.getTitle()).isEqualTo(updateParam.getTitle());
        assertThat(findPost.getBody()).isEqualTo(updateParam.getBody());
    }

    @Test
    void deletePost() {
        //given
        Post post = new Post("About Time", " We're all traveling through time together, e" +
                "very day of our lives. All we can do is do our best to relish this remarkable ride.");
        Post savedPost = postRepository.save(post);
        Long postId = savedPost.getId();

        // when
        postRepository.deleteById(postId);

        // then
        assertThat(postRepository.findById(postId)).isNull();
    }


}