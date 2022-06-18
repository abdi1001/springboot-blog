package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;

import java.util.List;

public interface CommentService {


  List<CommentResponseDTO> getAllComment(long id);



  CommentResponseDTO updateCommentById(long commentId, CommentRequestDTO commentRequestDTO);

  CommentResponseDTO deleteCommentById(long commentId);
}
