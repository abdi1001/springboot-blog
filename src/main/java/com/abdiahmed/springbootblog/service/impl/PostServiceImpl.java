package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Comment;
import com.abdiahmed.springbootblog.model.Post;
import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.requestDTO.CommentRequestDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.CreatePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.CommentResponseDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PageablePostDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PostResponseDTO;
import com.abdiahmed.springbootblog.repository.PostRepository;
import com.abdiahmed.springbootblog.service.interfaces.PostService;
import com.abdiahmed.springbootblog.service.mapper.CommentMapperImpl;
import com.abdiahmed.springbootblog.service.mapper.PostMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

  PostRepository postRepository;
  UserServiceImpl userService;
  //    ModelMapper modelMapper;
  PostMapperImpl postMapper;
  CommentMapperImpl commentMapper;
  CommentServiceImpl commentService;

  public PostServiceImpl(
      PostRepository postRepository,
      UserServiceImpl userService,
      PostMapperImpl postMapper,
      CommentServiceImpl commentService,
      CommentMapperImpl commentMapper) {
    this.postRepository = postRepository;
    //        this.modelMapper = modelMapper;
    this.userService = userService;
    this.postMapper = postMapper;
    this.commentMapper = commentMapper;
    this.commentService = commentService;
  }

  @Override
  public PostResponseDTO createPost(CreatePostDTO createPostDTO) {
    Post post = postMapper.mapToEntity(createPostDTO);
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    User foundUser = userService.findByName(username);

    post.setUser(foundUser);
    foundUser.addPostForUser(post);
    userService.saveUser(foundUser);

    Post savedPost = postRepository.save(post);
    return postMapper.mapToDTO(savedPost);
  }

  @Override
  public PageablePostDTO getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
    Sort sort =
        sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    Page<Post> page = postRepository.findAll(pageable);

    List<PostResponseDTO> posts =
        page.stream().map(post -> postMapper.mapToDTO(post)).collect(Collectors.toList());

    return PageablePostDTO.builder()
        .responseDTOList(posts)
        .pageSize(page.getSize())
        .pageNo(page.getNumber() + 1)
        .totalPages(page.getTotalPages())
        .totalElements(page.getTotalElements())
        .isLast(page.isLast())
        .build();
  }

  @Override
  public PostResponseDTO getPostById(long id) {
    Post post =
        postRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));

    return postMapper.mapToDTO(post);
  }

  public Post getPostByIdInternal(long id) {

    return postRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));
  }

  @Override
  public PostResponseDTO updatePost(long id, CreatePostDTO createPostDTO) {
    Post post = getPostByIdInternal(id);
    post.setTitle(createPostDTO.getTitle());
    post.setBody(createPostDTO.getBody());
    Post savedPost = postRepository.save(post);
    return postMapper.mapToDTO(savedPost);
  }

  @Override
  public void deletePost(long id) {
    Post post = getPostByIdInternal(id);
    postRepository.deleteById(id);
  }

  @Override
  public PostResponseDTO addCommentToPost(long postId, CommentRequestDTO commentRequestDTO) {
    Post post = getPostByIdInternal(postId);
    Comment comment1 = commentMapper.mapToEntity(commentRequestDTO);
    comment1.setPost(post);
    post.addComment(comment1);
    Post savedPost = postRepository.save(post);
    return postMapper.mapToDTO(savedPost);
  }

  @Override
  public CommentResponseDTO findCommentInPost(long postId, long commentId) {
    Post post = getPostByIdInternal(postId);
    Comment comment1 = commentService.findCommentByIdInternal(commentId);
    Optional<Comment> foundComment = post.getComments().stream().filter(comment -> comment == comment1).findFirst();
    Comment comment = foundComment.orElseThrow(() -> new ResourceNotFoundException("Post", "Comment Id", commentId));
    return commentMapper.mapToDTO(comment);
  }

//  public Comment findCommentByIdInternal(long postId, long commentId) {
//    Post post = getPostByIdInternal(postId);
//    Comment comment = commentService.g
//    if (!post.getId().equals(comment.getPost().getId())) {
//      throw new BlogAPIException("Comment does not belong to Post");
//    }
//    return comment;
//  }
}
