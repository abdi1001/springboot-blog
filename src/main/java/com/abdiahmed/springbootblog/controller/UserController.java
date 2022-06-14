package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;
import com.abdiahmed.springbootblog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping(consumes={"application/json"})
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User newUser = userService.getUserById(id);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageableUserDTO> getAllUsers( @RequestParam(value = "pageNo", defaultValue = "1", required = false) int pageNo,
                                                     @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
                                                     @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                                     @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableUserDTO newUser = userService.getAllUsers(pageNo,pageSize,sortBy,sortDir);

        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }
}
