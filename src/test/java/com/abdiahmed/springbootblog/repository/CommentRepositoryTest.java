package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.model.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    CommentRepository commentRepository;

    private Comment comment;

    @BeforeEach
    public void setup(){
        comment = new Comment();
        comment.setComment("New test comment");

    }

    //BDD style -- given_when_then
    @Test
    @DisplayName("Junit test for save comment")
    public void givenPostObject_whenSave_thenReturnSavedPost() {

        //given


        //when
        Comment savedComment = commentRepository.save(comment);

        //then

        assertThat(savedComment).isNotNull();
        assertThat(savedComment.getId()).isGreaterThan(0);

    }
}
