package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.MyAuthorities;

import java.util.List;

public interface AuthoritiesService {
    List<MyAuthorities> getAllAuthorities();

    MyAuthorities getAuthorityById(long id);

    MyAuthorities createAuthority(String authority) throws ResourceExist;

    MyAuthorities updateAuthority(long id, String role);

    MyAuthorities deleteAuthority(long id);

    MyAuthorities findAuthorityByName(String name);

    boolean authotiryExists(String name);

    MyAuthorities saveAuthority(MyAuthorities authority);

    List<MyAuthorities> SaveAuthoritiesList(List<MyAuthorities> authoritiesList);


}
