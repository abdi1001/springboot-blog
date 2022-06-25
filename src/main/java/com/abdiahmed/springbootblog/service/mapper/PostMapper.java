package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.payload.requestDTO.CreatePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.springframework.security.core.context.SecurityContextHolder;

@Mapper(componentModel = "spring", imports = SecurityContextHolder.class)
public interface PostMapper {

  Post mapToEntity(CreatePostDTO createPostDTO);

  @InheritInverseConfiguration
  PostResponseDTO mapToDTO(Post post);


  PostResponseDTO mapToDTO(CreatePostDTO post);
}
