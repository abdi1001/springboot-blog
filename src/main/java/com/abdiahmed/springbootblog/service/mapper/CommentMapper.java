package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment mapToEntity(CommentRequestDTO commentRequestDTO);
    CommentResponseDTO mapToDTO(Comment comment);
}
