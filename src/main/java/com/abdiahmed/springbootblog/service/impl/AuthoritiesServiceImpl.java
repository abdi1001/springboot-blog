package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import com.abdiahmed.springbootblog.repository.AuthoritiesRepository;
import com.abdiahmed.springbootblog.service.interfaces.AuthoritiesService;
import com.abdiahmed.springbootblog.service.mapper.AuthoritiesMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

  @Autowired
  AuthoritiesRepository authRepo;

  @Autowired
  AuthoritiesMapperImpl authoritiesMapper;

  @Override
  public List<Authorities> getAllAuthorities() {
    return authRepo.findAll();
  }

  @Override
  public Authorities getAuthorityById(long id) {
    return authRepo
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Authority", "Id", id));
  }

  @Override
  public Authorities createAuthority(String role) throws ResourceExist {

    if (authRepo.findByName(role).isPresent()) {
      throw new ResourceExist("Authority already exists");
    }
    Authorities newAuthority = new Authorities();
    newAuthority.setName(role);
    return authRepo.save(newAuthority);
  }

  @Override
  public Authorities updateAuthority(long id, String role) {
    Authorities foundAuthority = getAuthorityById(id);
    foundAuthority.setName(role);
    return authRepo.save(foundAuthority);
  }

  @Override
  public AuthoritiesResponseDTO deleteAuthority(long authoritiesId) {
   Authorities authorities = getAuthorityById(authoritiesId);
      authRepo.delete(authorities);

    return authoritiesMapper.mapToDTO(authorities);
  }

  @Override
  public AuthoritiesResponseDTO deleteAuthorityEverywhere(long authoritiesId) {
    Authorities authorities = getAuthorityById(authoritiesId);
    List<Role> collect = authorities.getRole().stream().filter(role -> role.getAuthorities().contains(authorities)).collect(Collectors.toList());

    for (Role role : collect ) {
        role.removeAuthority(authorities);
    }
    authRepo.delete(authorities);
    return authoritiesMapper.mapToDTO(authorities);

  }

  @Override
  public Authorities findAuthorityByName(String name) {
    return authRepo.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Authority", "name", name));
  }

  @Override
  public boolean authotiryExists(String name) {
    return authRepo.findByName(name).isPresent();
  }

  @Override
  public Authorities saveAuthority(Authorities authority) {
    return authRepo.save(authority);
  }

  @Override
  public List<Authorities> SaveAuthoritiesList(List<Authorities> authoritiesList) {
    return authRepo.saveAll(authoritiesList);
  }

  public void addRole(long id, Role role) {
    Authorities authorityById = getAuthorityById(id);
    authorityById.addRoleToAuthorities(role);
    authRepo.save(authorityById);
  }
}
