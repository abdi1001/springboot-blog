package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.payload.requestDTO.CreatePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring", imports = SecurityContextHolder.class)
public interface PostMapper {
  @Mapping(
      target = "username",
      expression = "java(SecurityContextHolder.getContext().getAuthentication().getName())")
  Post mapToEntity(CreatePostDTO createPostDTO);

  @InheritInverseConfiguration
  PostResponseDTO mapToDTO(Post post);

  @Mapping(
      target = "username",
      expression = "java(SecurityContextHolder.getContext().getAuthentication().getName())")
  PostResponseDTO mapToDTO(CreatePostDTO post);
}
