package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.CreatePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PageablePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;

public interface PostService {
    PostResponseDTO createPost(CreatePostDTO createPostDTO);
    PageablePostDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);
    PostResponseDTO getPostById(long id);
    PostResponseDTO updatePost(long id, CreatePostDTO createPostDTO);
    void deletePost(long id);


    PostResponseDTO addCommentToPost(long postId, CommentRequestDTO commentRequestDTO);
}
