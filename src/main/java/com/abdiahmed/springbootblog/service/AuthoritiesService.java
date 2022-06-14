package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Authorities;

import java.util.List;

public interface AuthoritiesService {
    List<Authorities> getAllAuthorities();

    Authorities getAuthorityById(long id);

    Authorities createAuthority(String authority) throws ResourceExist;

    Authorities updateAuthority(long id, String role);

    Authorities deleteAuthority(long id);

    Authorities findAuthorityByName(String name);

    boolean authotiryExists(String name);

    Authorities saveAuthority(Authorities authority);

    List<Authorities> SaveAuthoritiesList(List<Authorities> authoritiesList);


}
