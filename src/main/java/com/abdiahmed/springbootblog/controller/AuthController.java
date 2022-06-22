package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.payload.requestDTO.RegisterDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.SignInDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.UserResponseDTO;
import com.abdiahmed.springbootblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {

  @Autowired UserServiceImpl myUserService;

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> myUser(@RequestBody RegisterDTO registerDTO)
      throws ResourceExist {
    UserResponseDTO user = myUserService.registerUser(registerDTO);
    return new ResponseEntity<>(user, HttpStatus.CREATED);
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtToken> myUser(@RequestBody SignInDTO signInDTO) {
    JwtToken token = myUserService.signInUser(signInDTO);
    return new ResponseEntity<>(token, HttpStatus.CREATED);
  }
}
