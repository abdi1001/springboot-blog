package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    private Post post;

    @BeforeEach
    public void setup(){
        post = new Post();
        post.setTitle("New Post Title");
        post.setBody("New Post Body");

    }

    //BDD style -- given_when_then
    @Test
    @DisplayName("Junit test for save post")
    public void givenPostObject_whenSave_thenReturnSavedPost() {

        //given


        //when
        Post savedPost = postRepository.save(post);

        //then

        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Junit test for save post With Comments")
    public void givenPostObjectWithComment_whenSave_thenReturnSavedPost() {

        //given
        Comment comment = new Comment();
        comment.setComment("USER:READ");
        comment.setPost(post);
        post.addComment(comment);


        //when
        Post savedPost = postRepository.save(post);

        //then

        assertThat(savedPost).isNotNull();
        assertThat(savedPost.getId()).isGreaterThan(0);
        assertThat(savedPost.getComments().size()).isEqualTo(1);
        assertThat(savedPost.getComments().contains(comment)).isEqualTo(true);

    }


    @Test
    @DisplayName("Junit test for find all posts")
    public void givenPostList_whenFindAll_thenReturnPostList() {

        //given - precondition or setup

        Post post1 = new Post();
        post1.setTitle("Second Title");

        postRepository.save(post);
        postRepository.save(post1);


        //when - action or the behaviour that we are testing
        List<Post> postList = postRepository.findAll();

        //then - veryfy the output
        assertThat(postList).isNotNull();
        assertThat(postList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Junit test for save all posts")
    public void givenPostList_whenSaveAll_thenReturnPostList() {

        //given - precondition or setup

        Post post1 = new Post();
        post1.setTitle("Second Title");

        List<Post> postList = List.of(post,post1);


        //when - action or the behaviour that we are testing
        List<Post> foundPostList = postRepository.saveAll(postList);

        //then - veryfy the output
        assertThat(foundPostList).isNotNull();
        assertThat(foundPostList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Junit test for finding post by id")
    public void givenPostObject_whenFindById_thenReturnPostObject() {

        //given - precondition or setup

        Post savedPost = postRepository.save(post);


        //when - action or the behaviour that we are testing
        Post postInDb = postRepository.findById(savedPost.getId()).get();


        //then - veryfy the output
        assertThat(postInDb).isNotNull();

    }



    @Test
    @DisplayName("Junit test for deleting post with post object")
    public void givenPostObject_whenDelete_thenDeletePost() {

        //given - precondition or setup
        postRepository.save(post);


        //when - action or the behaviour that we are testing
        postRepository.delete(post);
        Optional<Post> postOptional = postRepository.findById(post.getId());


        //then - veryfy the output
        assertThat(postOptional).isEmpty();

    }

    @Test
    @DisplayName("Junit test for deleting post with post Id")
    public void givenPostId_whenDelete_thenDeletePost() {

        //given - precondition or setup
        Post post = new Post();
        post.setTitle("Test Comment");

        postRepository.save(post);


        //when - action or the behaviour that we are testing
        postRepository.deleteById(post.getId());
        Optional<Post> postOptional = postRepository.findById(post.getId());


        //then - veryfy the output
        assertThat(postOptional).isEmpty();

    }
}
