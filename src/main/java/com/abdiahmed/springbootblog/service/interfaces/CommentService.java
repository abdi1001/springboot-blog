package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;

import java.util.List;

public interface CommentService {

    CommentResponseDTO createComment(long postId, CommentRequestDTO commentRequestDTO);
    List<CommentResponseDTO> getAllComment(long id);
    CommentResponseDTO getCommentById(long postId,long commentId);
    CommentResponseDTO updateCommentById(long postId,long commentId, CommentRequestDTO commentRequestDTO);
    CommentResponseDTO deleteCommentById(long postId, long commentId);


}
