package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateUserDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.RegisterDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.SignInDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.UserResponseDTO;

public interface UserService {
  UserResponseDTO registerUser(RegisterDTO registerDTO) throws ResourceExist;

  JwtToken signInUser(SignInDTO signInDTO);

  User findByName(String name);

  long countUsers();

  User saveUser(User user);

  UserResponseDTO createUser(CreateUserDTO createUserDTO);

  PageableUserDTO getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);

  UserResponseDTO getUserById(long id);

  User updateUser(long userId, User user);

  void deleteUser(long id);
}
