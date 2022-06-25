package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;

import java.util.Set;

public interface AuthoritiesService {
    boolean authorityExists(String name);

    Set<Authorities> confirmAuthorityExistAndReturn(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet);

    Authorities findByName(String name);

    Authorities findById(long id);

    Set<Authorities> findAll();

    Authorities updateById(long id, CreateAuthoritiesDTO createAuthoritiesDTO);

    Set<Authorities> createAuthoritiesFromSet(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet);

//    boolean authoritiesNotExist(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet);

    void deleteAuthoritiesInSet(Set<CreateAuthoritiesDTO> createAuthoritiesDTOSet);
}
