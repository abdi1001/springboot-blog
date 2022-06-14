package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.requestDTO.RegisterDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.SignInDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;

public interface UserService {
    User registerUser(RegisterDTO registerDTO) throws ResourceExist;
    JwtToken signInUser(SignInDTO signInDTO);

    User findByName(String name);

    long countUsers();

    User saveUser(User user);

    User createUser(User user);
    PageableUserDTO getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);
    User getUserById(long id);
    User updateUser(long id, User user);
    void deleteUser(long id);
}
