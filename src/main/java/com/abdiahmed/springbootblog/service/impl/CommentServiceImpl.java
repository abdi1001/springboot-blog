package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import com.abdiahmed.springbootblog.repository.CommentRepository;
import com.abdiahmed.springbootblog.service.interfaces.CommentService;
// import org.modelmapper.ModelMapper;
import com.abdiahmed.springbootblog.service.mapper.CommentMapperImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

//  final PostServiceImpl postService;

  final CommentRepository commentRepository;
  //  ModelMapper modelMapper;
  final CommentMapperImpl commentMapper;

  public CommentServiceImpl(
//      PostServiceImpl postService,
      CommentRepository commentRepository,
      CommentMapperImpl commentMapper) {
    this.commentRepository = commentRepository;
    this.commentMapper = commentMapper;
    //    this.modelMapper = modelMapper;
  }

  @Override
  public List<CommentResponseDTO> getAllComment(long id) {

    List<Comment> postComment = commentRepository.findByPostId(id);
    return postComment.stream().map(commentMapper::mapToDTO).collect(Collectors.toList());
  }



  @Override
  public CommentResponseDTO updateCommentById(long commentId, CommentRequestDTO commentRequestDTO) {
    Comment comment = findCommentByIdInternal(commentId);
    comment.setComment(comment.getComment());
    commentRepository.save(comment);
    return commentMapper.mapToDTO(comment);
  }

  @Override
  public CommentResponseDTO deleteCommentById(long commentId) {
    Comment comment = findCommentByIdInternal(commentId);
    commentRepository.delete(comment);
    return commentMapper.mapToDTO(comment);
  }

  public Comment findCommentByIdInternal(long commentId) {
    return commentRepository
            .findById(commentId)
            .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentId));
  }


}
