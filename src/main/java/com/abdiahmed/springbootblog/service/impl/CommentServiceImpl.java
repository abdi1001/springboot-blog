package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import com.abdiahmed.springbootblog.repository.CommentRepository;
import com.abdiahmed.springbootblog.service.interfaces.CommentService;
import com.abdiahmed.springbootblog.service.mapper.CommentMapperImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

  final CommentRepository commentRepository;
  final CommentMapperImpl commentMapper;

  public CommentServiceImpl(CommentRepository commentRepository, CommentMapperImpl commentMapper) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
  }

  @Override
  public List<CommentResponseDTO> getAllComment(long id) {

    List<Comment> postComment = commentRepository.findByPostId(id);
    return postComment.stream().map(commentMapper::mapToDTO).collect(Collectors.toList());
  }

  public Comment findCommentByIdInternal(long commentId) {
    return commentRepository
        .findById(commentId)
        .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
  }
}
