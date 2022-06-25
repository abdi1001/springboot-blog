package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.repository.AuthoritiesRepository;
import com.abdiahmed.springbootblog.service.interfaces.AuthoritiesService;
import com.abdiahmed.springbootblog.service.mapper.AuthoritiesMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

  @Autowired AuthoritiesRepository authoritiesRepository;
  @Autowired
  AuthoritiesMapperImpl authoritiesMapper;

  @Override
  public boolean authorityExists(String name) {
    return authoritiesRepository.findByName(name).isPresent();
  }

  @Override
  public Set<Authorities> confirmAuthorityExistAndReturn(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet) {
    for(CreateAuthoritiesDTO authorities : createAuthoritiesDTOSet) {
      boolean authorityExists = authorityExists(authorities.getName());
      if(!authorityExists) {
        throw new ResourceExist("Authority does not exists: " + authorities.getName());
      }
    }
    Set<Authorities> authoritiesSet = new HashSet<>();
    for (CreateAuthoritiesDTO createAuthoritiesDTO: createAuthoritiesDTOSet) {
      Authorities foundAuthority = findByName(createAuthoritiesDTO.getName());
      authoritiesSet.add(foundAuthority);
    }
    return authoritiesSet;
  }
  @Override
  public Authorities findByName(String name) {
    return authoritiesRepository.findByName(name).orElseThrow(()-> new ResourceNotFoundException("Authority", "Id", name));
  }
  @Override
  public Authorities findById(long id) {
    return authoritiesRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Authority", "Id", id));
  }

  @Override
  public Set<Authorities> findAll() {
    return new HashSet<>(authoritiesRepository.findAll());
  }

  @Override
  public Authorities updateById(long id, CreateAuthoritiesDTO createAuthoritiesDTO) {
    Authorities authority = findById(id);
    authority.setName(createAuthoritiesDTO.getName());
    authoritiesRepository.save(authority);
    return  authority;

  }

  @Override
  public Set<Authorities> createAuthoritiesFromSet(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet) {
    for(CreateAuthoritiesDTO authorities : createAuthoritiesDTOSet) {
      boolean authorityExists = authorityExists(authorities.getName());
      if(authorityExists) {
        throw new ResourceExist("Authority already exists: " + authorities.getName());
      }
    }
    Set<Authorities> authoritiesSet = authoritiesMapper.mapToEntity(createAuthoritiesDTOSet);
    List<Authorities> savedAuthorities = authoritiesRepository.saveAll(authoritiesSet);
    return new HashSet<>(savedAuthorities);
  }


  @Override
  @Transactional
  public void deleteAuthoritiesInSet(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet) {
    for(CreateAuthoritiesDTO authorities : createAuthoritiesDTOSet) {
      boolean authorityExists = authorityExists(authorities.getName());
      if(!authorityExists) {
        throw new ResourceExist("Authority does not exists: " + authorities.getName());
      } else {
        Authorities deleteAuthority = findByName(authorities.getName());
        authoritiesRepository.delete(deleteAuthority);
      }
    }
  }
}
