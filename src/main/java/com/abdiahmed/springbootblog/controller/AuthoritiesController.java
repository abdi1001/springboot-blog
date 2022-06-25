package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.service.interfaces.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1/authorities")
public class AuthoritiesController {
  @Autowired
  AuthoritiesService authoritiesService;
  @PostMapping()
  public ResponseEntity<Set<Authorities>> createAuthorities(@RequestBody Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet){
    Set<Authorities> authoritiesFromSet = authoritiesService.createAuthoritiesFromSet(createAuthoritiesDTOSet);
    return new ResponseEntity<>(authoritiesFromSet, HttpStatus.OK);
  }

  @PostMapping("{id}")
  public ResponseEntity<Authorities> updateAuthorities(@PathVariable long id ,@RequestBody CreateAuthoritiesDTO createAuthoritiesDTO){
    Authorities authorities = authoritiesService.updateById(id,createAuthoritiesDTO);
    return new ResponseEntity<>(authorities, HttpStatus.OK);
  }

  @DeleteMapping()
  public ResponseEntity<String> deleteAuthorities(@RequestBody Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet)  {
    authoritiesService.deleteAuthoritiesInSet(createAuthoritiesDTOSet);
    return new ResponseEntity<>("Authorities Deleted", HttpStatus.OK);
  }

  @GetMapping("{id}")
  public ResponseEntity<Authorities> deleteAuthorities(@PathVariable long id) throws ResourceExist {
    Authorities Authority = authoritiesService.findById(id);
    return new ResponseEntity<>(Authority, HttpStatus.OK);
  }
  @GetMapping()
  public ResponseEntity<Set<Authorities>> findAll() throws ResourceExist {
    Set<Authorities> Authority = authoritiesService.findAll();
    return new ResponseEntity<>(Authority, HttpStatus.OK);
  }


}
