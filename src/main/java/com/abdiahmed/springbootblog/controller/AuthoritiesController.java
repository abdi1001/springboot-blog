package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import com.abdiahmed.springbootblog.service.impl.AuthoritiesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authorities")
public class AuthoritiesController {

    @Autowired
    AuthoritiesServiceImpl authService;

    @GetMapping()
    public ResponseEntity<List<Authorities>> getAllAuthorities(){
        List<Authorities> authoritys = authService.getAllAuthorities();
        return new ResponseEntity<>(authoritys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authorities> getAuthorityById(@PathVariable long id){
        Authorities authority = authService.getAuthorityById(id);
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Authorities> createAuthority(@RequestBody Authorities authority) throws ResourceExist {
        Authorities newAuthority = authService.createAuthority(authority.getName());
        return new ResponseEntity<>(newAuthority, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Authorities> updateAuthority(@PathVariable long id, @RequestBody Authorities authority){
        Authorities updatedAuthority = authService.updateAuthority(id, authority.getName());
        return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthoritiesResponseDTO> updateAuthority(@PathVariable long id){
        AuthoritiesResponseDTO updatedAuthority = authService.deleteAuthorityEverywhere(id);
        return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);
    }


}
