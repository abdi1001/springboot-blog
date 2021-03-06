package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.payload.requestDTO.PostRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PageablePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import com.abdiahmed.springbootblog.service.impl.PostServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class PostController {

  private PostServiceImpl postService;

  public PostController(PostServiceImpl postService) {
    this.postService = postService;
  }

  @PostMapping("/posts")
  public ResponseEntity<PostResponseDTO> createPost(@RequestBody PostRequestDTO postRequestDTO) {
    PostResponseDTO postResponseDTO = postService.createPost(postRequestDTO);
    return new ResponseEntity<>(postResponseDTO, HttpStatus.CREATED);
  }

  @GetMapping("/posts")
  public ResponseEntity<PageablePostDTO> getAllPosts(
      @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {

    PageablePostDTO pageablePostDTO =
        postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    return new ResponseEntity<>(pageablePostDTO, HttpStatus.OK);
  }

  @GetMapping("/posts/{id}")
  public ResponseEntity<PostResponseDTO> getPostById(@PathVariable long id) {
    PostResponseDTO postResponseDTOS = postService.getPostById(id);
    return new ResponseEntity<>(postResponseDTOS, HttpStatus.OK);
  }

  @PutMapping("/posts/{id}")
  public ResponseEntity<PostResponseDTO> updatePost(
      @PathVariable long id, @RequestBody PostRequestDTO postRequestDTO) {
    PostResponseDTO postResponseDTOS = postService.updatePost(id, postRequestDTO);
    return new ResponseEntity<>(postResponseDTOS, HttpStatus.OK);
  }

  @DeleteMapping("/posts/{id}")
  public ResponseEntity<String> deletePostById(@PathVariable long id) {
    postService.deletePost(id);
    return new ResponseEntity<>("Post deleted", HttpStatus.OK);
  }
}
