package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.payload.requestDTO.PostRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PageablePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;

public interface PostService {
    PostResponseDTO createPost(PostRequestDTO postRequestDTO);
    PageablePostDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostResponseDTO getPostById(long id);
    PostResponseDTO updatePost(long id, PostRequestDTO postRequestDTO);
    void deletePost(long id);

}
