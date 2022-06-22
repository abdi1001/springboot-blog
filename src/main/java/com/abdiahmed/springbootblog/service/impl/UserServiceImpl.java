package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.requestDTO.*;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.UserResponseDTO;
import com.abdiahmed.springbootblog.repository.UserRepository;
import com.abdiahmed.springbootblog.security.JwtTokenProvider;
import com.abdiahmed.springbootblog.service.interfaces.UserService;
import com.abdiahmed.springbootblog.service.mapper.UserMapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  UserRepository userRepo;
  RoleServiceImpl roleService;
  AuthoritiesServiceImpl authoritiesService;
  BCryptPasswordEncoder passwordEncoder;

  AuthenticationManager authenticationManager;

  JwtTokenProvider jwtTokenProvider;

  //  ModelMapper modelMapper;

  UserMapperImpl userMapper;

  public UserServiceImpl(
      UserRepository userRepo,
      BCryptPasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      RoleServiceImpl roleService,
      //      ModelMapper modelMapper,
      UserMapperImpl userMapper,
      AuthoritiesServiceImpl authoritiesService) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.roleService = roleService;
    this.authoritiesService = authoritiesService;
    //    this.modelMapper = modelMapper;
    this.userMapper = userMapper;
  }

  @Override
  public UserResponseDTO registerUser(RegisterDTO user) throws ResourceExist {

    if (userRepo.count() == 0) {

      Role adminRole = roleService.createRole("ROLE_ADMIN");
      Role userRole = roleService.createRole("ROLE_USER");

      List<CreateAuthoritiesDTO> adminAuthoritiesList =
          List.of(
              CreateAuthoritiesDTO.builder().name("User:Create").build(),
              CreateAuthoritiesDTO.builder().name("User:Read").build(),
              CreateAuthoritiesDTO.builder().name("User:Update").build(),
              CreateAuthoritiesDTO.builder().name("User:Delete").build());

      roleService.addAuthoritiesToRole(adminRole.getId(), adminAuthoritiesList);

      CreateAuthoritiesDTO userAuthoritiesList =
          CreateAuthoritiesDTO.builder().name("User:Read").build();

      roleService.addAuthorityToRole(userRole.getId(), userAuthoritiesList);

      return getUserResponseDTO(user, adminRole);

    } else {
      Role userRole;
      if (roleService.roleDoesNotExists("ROLE_USER")) {
        userRole = roleService.createRole("ROLE_USER");
        CreateAuthoritiesDTO userAuthoritiesList =
            CreateAuthoritiesDTO.builder().name("User:Read").build();
        roleService.addAuthorityToRole(userRole.getId(), userAuthoritiesList);
      }
      userRole = roleService.getRoleByName("ROLE_USER");

      return getUserResponseDTO(user, userRole);
    }
  }

  private UserResponseDTO getUserResponseDTO(RegisterDTO user, Role userRole) {
    User myUser =
        User.builder()
            .name(user.getName())
            .username(user.getUsername())
            .email(user.getEmail())
            .password(passwordEncoder.encode(user.getPassword()))
            .role(Set.of(userRole))
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();

    User savedUser = userRepo.save(myUser);
    return mapToDTO(savedUser);
  }

  private UserResponseDTO mapToDTO(User savedUser) {
    return userMapper.mapToDTO(savedUser);
  }

  @Override
  public JwtToken signInUser(SignInDTO signInDTO) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signInDTO.getUsername(), signInDTO.getPassword()));

    String token = jwtTokenProvider.createJwtToken(auth);
    return new JwtToken(token);
  }

  @Override
  public User findByName(String name) {
    return userRepo
        .findByUsername(name)
        .orElseThrow(() -> new ResourceNotFoundException("User", "name", name));
  }

  @Override
  public long countUsers() {
    return userRepo.count();
  }

  @Override
  public User saveUser(User user) {
    return userRepo.save(user);
  }

  @Override
  public UserResponseDTO createUser(CreateUserDTO createUserDTO) {
    HashSet<Role> roleSet = new HashSet<>();
    for (CreateRoleDTO role : createUserDTO.getRoleName()) {
      Role foundRole = roleService.getRoleByName(role.getName());
      roleSet.add(foundRole);
    }

    User myUser =
        User.builder()
            .name(createUserDTO.getUsername())
            .username(createUserDTO.getUsername())
            .email(createUserDTO.getEmail())
            .password(passwordEncoder.encode(createUserDTO.getPassword()))
            .role(roleSet)
            .isAccountNonExpired(true)
            .isAccountNonLocked(true)
            .isCredentialsNonExpired(true)
            .isEnabled(true)
            .build();

    User savedUser = userRepo.save(myUser);

    return mapToDTO(savedUser);
  }

  @Override
  public PageableUserDTO getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
    Sort sort =
        sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
    Page<User> page = userRepo.findAll(pageable);

    List<User> users = page.stream().collect(Collectors.toList());

    PageableUserDTO pageableUserDTO =
        PageableUserDTO.builder()
            .pageSize(page.getSize())
            .pageNo(page.getNumber() + 1)
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .isLast(page.isLast())
            .build();

    List<UserResponseDTO> userResponseDTOList = new ArrayList<>();

    for (User user : users) {
      UserResponseDTO userResponseDTO = userMapper.mapToDTO(user);
      userResponseDTOList.add(userResponseDTO);
    }
    pageableUserDTO.setResponseDTOList(userResponseDTOList);

    return pageableUserDTO;
  }

  public UserResponseDTO addRoleToUser(long userId, CreateRoleDTO createRoleDTO) {
    Role foundRole = roleService.getRoleByName(createRoleDTO.getName());
    User foundUser = getUserByIdInternal(userId);
    foundUser
        .getRole()
        .forEach(
            role -> {
              if (role.getName().equalsIgnoreCase(createRoleDTO.getName())) {
                throw new ResourceExist("User already has the role " + createRoleDTO.getName());
              }
            });
    foundUser.addRoleToUser(foundRole);
    User saveUser = userRepo.save(foundUser);
    return userMapper.mapToDTO(saveUser);
  }

  public UserResponseDTO removeRoleFromUser(long userId, long roleId) {
    Role foundRole = roleService.getRoleById(roleId);
    User foundUser = getUserByIdInternal(userId);
    foundUser.removeRoleFromUser(foundRole);
    User saveUser = userRepo.save(foundUser);
    return userMapper.mapToDTO(saveUser);
  }

  public String removeRoleFromAllUsers(long roleId) {
    Role foundRole = roleService.getRoleById(roleId);
    List<User> users = foundRole.getUsers();
    users.forEach(user -> user.removeRoleFromUser(foundRole));
    List<User> updatedUserList = userRepo.saveAll(users);

    return "Role removed from all users";
  }

  @Override
  public UserResponseDTO getUserById(long id) {
    User user =
        userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    return mapToDTO(user);
  }

  public User getUserByIdInternal(long id) {
    return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  }

  @Override
  public User updateUser(long userId, User user) {
    User foundUser = getUserByIdInternal(userId);
  return foundUser;
  }

  @Override
  public void deleteUser(long id) {}
}
