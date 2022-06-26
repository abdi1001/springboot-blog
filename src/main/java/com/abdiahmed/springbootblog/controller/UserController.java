package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.payload.requestDTO.CreateRoleDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateUserDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.UserResponseDTO;
import com.abdiahmed.springbootblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

  @Autowired UserServiceImpl userService;

  @PostMapping()
  public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserDTO user) {
    UserResponseDTO newUser = userService.createUser(user);

    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
    UserResponseDTO newUser = userService.getUserById(id);

    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @GetMapping()
  public ResponseEntity<PageableUserDTO> getAllUsers(
      @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
    PageableUserDTO newUser = userService.getAllUsers(pageNo, pageSize, sortBy, sortDir);

    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

//  @GetMapping("/{id}")
//  public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
//    UserResponseDTO newUser = userService.getUserById(id);
//
//    return new ResponseEntity<>(newUser, HttpStatus.OK);
//  }

  @PostMapping("/{userId}/addRole")
  public ResponseEntity<UserResponseDTO> addRoleToUser(@PathVariable long userId, @RequestBody CreateRoleDTO createRoleDTO) {
    UserResponseDTO newUser = userService.addRoleToUser(userId,createRoleDTO);
    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @DeleteMapping("/{userId}/removeRole/{roleId}")
  public ResponseEntity<UserResponseDTO> removeRoleFromUser(@PathVariable long userId,@PathVariable long roleId) {
    UserResponseDTO newUser = userService.removeRoleFromUser(userId,roleId);
    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }

  @DeleteMapping("/removeRole/{roleId}")
  public ResponseEntity<String> removeRoleFromAllUsers(@PathVariable long roleId) {
    String newUser = userService.removeRoleFromAllUsers(roleId);
    return new ResponseEntity<>(newUser, HttpStatus.OK);
  }
  @DeleteMapping("/removeUser/{userId}")
  public ResponseEntity<String> deleteUser(@PathVariable long userId) {
    userService.deleteUser(userId);
    return new ResponseEntity<>("User Deleted", HttpStatus.OK);
  }
}
