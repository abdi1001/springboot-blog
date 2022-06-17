package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.payload.requestDTO.CreatePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post mapToEntity(CreatePostDTO createPostDTO);
    PostResponseDTO mapToDTO(Post post);
    PostResponseDTO mapToDTO(CreatePostDTO post);
}
