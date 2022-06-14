package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.BlogAPIException;
import com.abdiahmed.springbootblog.model.MyUser;
import com.abdiahmed.springbootblog.repository.MyUserRepository;
import com.abdiahmed.springbootblog.security.MyPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    MyUserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user =
                userRepository.findByUsername(username)
                .orElseThrow(() -> new BlogAPIException("User with username " + username + " not found"));

        return new MyPrincipal(user);
    }
}
