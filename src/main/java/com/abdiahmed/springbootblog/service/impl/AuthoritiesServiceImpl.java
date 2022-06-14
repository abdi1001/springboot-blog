package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.MyAuthorities;
import com.abdiahmed.springbootblog.model.MyRoles;
import com.abdiahmed.springbootblog.repository.MyAuthoritiesRepository;
import com.abdiahmed.springbootblog.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

  @Autowired MyAuthoritiesRepository authRepo;

  @Override
  public List<MyAuthorities> getAllAuthorities() {
    return authRepo.findAll();
  }

  @Override
  public MyAuthorities getAuthorityById(long id) {
    return authRepo
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Authority", "Id", id));
  }

  @Override
  public MyAuthorities createAuthority(String role) throws ResourceExist {

    if (authRepo.findByName(role).isPresent()) {
      throw new ResourceExist("Authority already exists");
    }
    MyAuthorities newAuthority = new MyAuthorities();
    newAuthority.setName(role);
    return authRepo.save(newAuthority);
  }

  @Override
  public MyAuthorities updateAuthority(long id, String role) {
    MyAuthorities foundAuthority = getAuthorityById(id);
    foundAuthority.setName(role);
    return authRepo.save(foundAuthority);
  }

  @Override
  public MyAuthorities deleteAuthority(long id) {
    MyAuthorities deleteAuthority = getAuthorityById(id);
    authRepo.delete(deleteAuthority);
    return deleteAuthority;
  }

  @Override
  public MyAuthorities findAuthorityByName(String name) {
    return authRepo.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Authority", "name", name));
  }

  @Override
  public boolean authotiryExists(String name) {
    return authRepo.findByName(name).isPresent();
  }

  @Override
  public MyAuthorities saveAuthority(MyAuthorities authority) {
    return authRepo.save(authority);
  }

  @Override
  public List<MyAuthorities> SaveAuthoritiesList(List<MyAuthorities> authoritiesList) {
    return authRepo.saveAll(authoritiesList);
  }

  public void addRole(long id, MyRoles role) {
    MyAuthorities authorityById = getAuthorityById(id);
    authorityById.setRoles(role);
    authRepo.save(authorityById);
  }
}
