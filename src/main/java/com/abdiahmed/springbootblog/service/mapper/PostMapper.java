package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.payload.requestDTO.CreatePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring", imports = SecurityContextHolder.class)
public interface PostMapper {

  Post mapToEntity(CreatePostDTO createPostDTO);

  @Mapping(target = "username", source = "user.username")
  PostResponseDTO mapToDTO(Post post);


  PostResponseDTO mapToDTO(CreatePostDTO post);

  @Mapping(target = "username", source = "user.username")
  CommentResponseDTO mapToDTO(Comment comment);
}
