package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import com.abdiahmed.springbootblog.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class CommentController {

  @Autowired private CommentServiceImpl commentService;

  public CommentController(CommentServiceImpl commentService) {
    this.commentService = commentService;
  }

  @PostMapping("/posts/{id}/comments")
  public ResponseEntity<CommentResponseDTO> createComment(
      @PathVariable long id, @RequestBody CommentRequestDTO commentRequestDTO) {
    CommentResponseDTO commentResponseDTO = commentService.createComment(id, commentRequestDTO);
    return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
  }

  @GetMapping("/posts/{id}/comments")
  public ResponseEntity<List<CommentResponseDTO>> getAllCommentsByIds(@PathVariable long id) {
    List<CommentResponseDTO> commentResponseDTO = commentService.getAllComment(id);
    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
  }

  @GetMapping("/posts/{id}/comments/{commentId}")
  public ResponseEntity<CommentResponseDTO> getCommentByIds(
      @PathVariable("id") long postId, @PathVariable("commentId") long commentId) {
    CommentResponseDTO commentResponseDTO = commentService.getCommentById(postId, commentId);
    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
  }

  @PutMapping("/posts/{id}/comments/{commentId}")
  public ResponseEntity<CommentResponseDTO> updateComment(
      @PathVariable("id") long postId,
      @PathVariable("commentId") long commentId,
      @RequestBody CommentRequestDTO commentRequestDTO) {
    CommentResponseDTO commentResponseDTO =
        commentService.updateCommentById(postId, commentId, commentRequestDTO);
    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
  }

  @DeleteMapping("/posts/{id}/comments/{commentId}")
  public ResponseEntity<CommentResponseDTO> deleteComment(
      @PathVariable("id") long postId, @PathVariable("commentId") long commentId) {
    CommentResponseDTO commentResponseDTO = commentService.deleteCommentById(postId, commentId);
    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
  }
}
