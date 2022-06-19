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

//  @PostMapping("/posts/{id}/comments")
//  public ResponseEntity<CommentResponseDTO> createComment(
//      @PathVariable long id, @RequestBody CommentRequestDTO commentRequestDTO) {
//    CommentResponseDTO commentResponseDTO = commentService.createComment(id, commentRequestDTO);
//    return new ResponseEntity<>(commentResponseDTO, HttpStatus.CREATED);
//  }

//  @GetMapping("/posts/{id}/comments")
//  public ResponseEntity<List<CommentResponseDTO>> getAllCommentsByIds(@PathVariable long id) {
//    List<CommentResponseDTO> commentResponseDTO = commentService.getAllComment(id);
//    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
//  }

//  @GetMapping("/posts/{id}/comments/{commentId}")
//  public ResponseEntity<CommentResponseDTO> getCommentByIds(
//      @PathVariable("id") long postId, @PathVariable("commentId") long commentId) {
//    CommentResponseDTO commentResponseDTO = commentService.getCommentById(postId, commentId);
//    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
//  }

//  @PutMapping("/posts/{id}/comments/{commentId}")
//  public ResponseEntity<CommentResponseDTO> updateComment(
//      @PathVariable("id") long postId,
//      @PathVariable("commentId") long commentId,
//      @RequestBody CommentRequestDTO commentRequestDTO) {
//    CommentResponseDTO commentResponseDTO =
//        commentService.updateCommentById(commentId, commentRequestDTO);
//    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
//  }

//  @DeleteMapping("/posts/{id}/comments/{commentId}")
//  public ResponseEntity<CommentResponseDTO> deleteComment(
//      @PathVariable("id") long postId, @PathVariable("commentId") long commentId) {
//    CommentResponseDTO commentResponseDTO = commentService.deleteCommentById(commentId);
//    return new ResponseEntity<>(commentResponseDTO, HttpStatus.OK);
//  }
}
