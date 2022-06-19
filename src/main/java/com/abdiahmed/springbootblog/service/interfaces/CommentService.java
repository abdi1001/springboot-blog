package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;

import java.util.List;

public interface CommentService {


  List<CommentResponseDTO> getAllComment(long id);



//  Comment updateCommentById(long commentId, CommentRequestDTO commentRequestDTO);

  void deleteCommentById(long commentId);
}
