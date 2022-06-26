package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring", imports = SecurityContextHolder.class)
public interface CommentMapper {
    Comment mapToEntity(CommentRequestDTO commentRequestDTO);

    @Mapping(target = "username", source = "user.username")
    CommentResponseDTO mapToDTO(Comment comment);

    @InheritInverseConfiguration
    Comment mapToDTO(CommentResponseDTO comment);
}
