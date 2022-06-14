package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
