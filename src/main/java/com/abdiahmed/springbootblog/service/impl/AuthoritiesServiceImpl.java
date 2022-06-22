package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.repository.AuthoritiesRepository;
import com.abdiahmed.springbootblog.service.interfaces.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

  @Autowired AuthoritiesRepository authRepo;

  public boolean authorityExists(String name) {
    return authRepo.findByName(name).isPresent();
  }

  public Authorities findByName(String name) {
    return authRepo.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Authority", "Id", name));
  }
}
