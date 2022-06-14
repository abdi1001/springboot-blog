package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.BlogAPIException;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import com.abdiahmed.springbootblog.repository.CommentRepository;
import com.abdiahmed.springbootblog.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

  final PostServiceImpl postService;

  final CommentRepository commentRepository;
  ModelMapper modelMapper;

  public CommentServiceImpl(
      PostServiceImpl postService, CommentRepository commentRepository, ModelMapper modelMapper) {
    this.postService = postService;
    this.commentRepository = commentRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public CommentResponseDTO createComment(long postId, CommentRequestDTO commentRequestDTO) {
    Post post = postService.getPostByIdInternal(postId);
    Comment comment = mapToEntity(commentRequestDTO);
    comment.setPost(post);
    Comment savedCommet = commentRepository.save(comment);
    return mapToDTO(savedCommet);
  }

  @Override
  public List<CommentResponseDTO> getAllComment(long id) {

    List<Comment> postComment = commentRepository.findByPostId(id);
    return postComment.stream().map(this::mapToDTO).collect(Collectors.toList());
  }

  @Override
  public CommentResponseDTO getCommentById(long postId, long commentId) {
    Comment comment = findCommentByIdInternal(postId, commentId);
    return mapToDTO(comment);
  }

  public Comment findCommentByIdInternal(long postId, long commentId) {
    Post post = postService.getPostByIdInternal(postId);
    Comment comment =
        commentRepository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
    if (!post.getId().equals(comment.getPost().getId())) {
      throw new BlogAPIException("Comment does not belong to Post");
    }
    return comment;
  }

  @Override
  public CommentResponseDTO updateCommentById(
      long postId, long commentId, CommentRequestDTO commentRequestDTO) {
    Comment comment = findCommentByIdInternal(postId, commentId);
    comment.setName(commentRequestDTO.getName());
    comment.setComment(commentRequestDTO.getComment());
    Comment updatedComment = commentRepository.save(comment);
    return mapToDTO(updatedComment);
  }

  @Override
  public CommentResponseDTO deleteCommentById(long postId, long commentId) {
    Comment comment = findCommentByIdInternal(postId, commentId);
    commentRepository.delete(comment);
    return mapToDTO(comment);
  }

  private Comment mapToEntity(CommentRequestDTO commentRequestDTO) {
    Comment comment = modelMapper.map(commentRequestDTO, Comment.class);
    return comment;
  }

  private CommentResponseDTO mapToDTO(Comment savedCommet) {
    CommentResponseDTO commentResponseDTO = modelMapper.map(savedCommet, CommentResponseDTO.class);
    return commentResponseDTO;
  }
}
