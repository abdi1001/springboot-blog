package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1")
public class CommentController {

  @Autowired private CommentServiceImpl commentService;

  public CommentController(CommentServiceImpl commentService) {
    this.commentService = commentService;
  }
}
