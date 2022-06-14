package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.MyUser;
import com.abdiahmed.springbootblog.payload.requestDTO.RegisterDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.SignInDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;

public interface UserService {
    MyUser registerUser(RegisterDTO registerDTO) throws ResourceExist;
    JwtToken signInUser(SignInDTO signInDTO);

    MyUser findByName(String name);

    long countUsers();

    MyUser saveUser(MyUser user);

    MyUser createUser(MyUser user);
    PageableUserDTO getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir);
    MyUser getUserById(long id);
    MyUser updateUser(long id, MyUser user);
    void deleteUser(long id);
}
