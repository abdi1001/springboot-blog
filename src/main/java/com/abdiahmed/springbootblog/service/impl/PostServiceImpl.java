package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.MyUser;
import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.payload.requestDTO.PostRequestDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PageablePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import com.abdiahmed.springbootblog.repository.PostRepository;
import com.abdiahmed.springbootblog.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    PostRepository postRepository;
    UserServiceImpl userService;
    ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper, UserServiceImpl userService) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @Override
    public PostResponseDTO createPost(PostRequestDTO postRequestDTO) {
        Post post = mapToEntity(postRequestDTO);
        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = user.getUsername();
        MyUser foundUser = userService.findByName(username);

        post.setUser(foundUser);
        postRepository.save(post);
        foundUser.addPostForUser(post);



        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public PageablePostDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo - 1,pageSize,sort);
        Page<Post> page = postRepository.findAll(pageable);

        List<PostResponseDTO> posts = page.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());

        PageablePostDTO pageablePostDTO = PageablePostDTO.builder()
                .responseDTOList(posts)
                .pageSize(page.getSize())
                .pageNo(page.getNumber() + 1)
                .totalPages(page.getTotalPages())
                .totalElements(page.getTotalElements())
                .isLast(page.isLast())
                .build();
        return pageablePostDTO;
    }

    @Override
    public PostResponseDTO getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));

        return mapToDTO(post);
    }

    public Post getPostByIdInternal(long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));

        return post;
    }

    @Override
    public PostResponseDTO updatePost(long id, PostRequestDTO postRequestDTO) {
        Post post = getPostByIdInternal(id);
        post.setTitle(postRequestDTO.getTitle());
        post.setBody(postRequestDTO.getBody());
        Post savedPost = postRepository.save(post);
        return mapToDTO(savedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = getPostByIdInternal(id);
        postRepository.delete(post);
    }

    private Post mapToEntity(PostRequestDTO postRequestDTO) {
        Post post = modelMapper.map(postRequestDTO, Post.class);
        return post;
    }

    private PostResponseDTO mapToDTO(Post post) {
        PostResponseDTO postResponseDTO = new PostResponseDTO();
        postResponseDTO = modelMapper.map(post, PostResponseDTO.class);
        return postResponseDTO;
    }

}
