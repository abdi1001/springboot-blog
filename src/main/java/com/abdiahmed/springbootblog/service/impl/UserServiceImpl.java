package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.model.Roles;
import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.requestDTO.RegisterDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.SignInDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;
import com.abdiahmed.springbootblog.repository.UserRepository;
import com.abdiahmed.springbootblog.security.JwtTokenProvider;
import com.abdiahmed.springbootblog.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
  UserRepository userRepo;
  RoleServiceImpl roleService;
  AuthoritiesServiceImpl authoritiesService;
  BCryptPasswordEncoder passwordEncoder;

  AuthenticationManager authenticationManager;

  JwtTokenProvider jwtTokenProvider;

  public UserServiceImpl(
      UserRepository userRepo,
      BCryptPasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      RoleServiceImpl roleService,
      AuthoritiesServiceImpl authoritiesService) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.roleService = roleService;
    this.authoritiesService = authoritiesService;
  }

  @Override
  public User registerUser(RegisterDTO user) throws ResourceExist {

    if (userRepo.count() == 0) {
      if(roleService.roleDoesNotExist("ROLE_ADMIN")){

      }
      Roles adminRole = roleService.createRole("ROLE_ADMIN");

      List<Authorities> authoritiesList = new ArrayList<>();

      Authorities authorities1 = authoritiesService.createAuthority("User:Read");
      authoritiesService.addRole(authorities1.getId(),adminRole);

      Authorities authorities2 = authoritiesService.createAuthority("User:Create");
      authoritiesService.addRole(authorities2.getId(),adminRole);

      Authorities authorities3 = authoritiesService.createAuthority("User:Update");
      authoritiesService.addRole(authorities3.getId(),adminRole);

      Authorities authorities4 = authoritiesService.createAuthority("User:Delete");
      authoritiesService.addRole(authorities4.getId(),adminRole);

      authoritiesList.add(authorities1);
      authoritiesList.add(authorities2);
      authoritiesList.add(authorities3);
      authoritiesList.add(authorities4);

      List<Authorities> mySavedAuthorities = authoritiesService.SaveAuthoritiesList(authoritiesList);

      for(Authorities authority : mySavedAuthorities) {
        adminRole.addAuthority(authority);
      }
      Roles savedRole = roleService.saveRole(adminRole);

      User myUser =
              User.builder()
                      .name(user.getName())
                      .username(user.getUsername())
                      .email(user.getEmail())
                      .password(passwordEncoder.encode(user.getPassword()))
                      .roles(Collections.singleton(savedRole))
                      .isAccountNonExpired(true)
                      .isAccountNonLocked(true)
                      .isCredentialsNonExpired(true)
                      .isEnabled(true)
                      .build();
      return userRepo.save(myUser);

    } else {
      Roles userRole = null;
      if(roleService.roleDoesNotExist("ROLE_USER")) {
        Roles newUserRole = roleService.createRole("ROLE_USER");

        Authorities authorities = authoritiesService.findAuthorityByName("User:Read");
        authorities.setRoles(newUserRole);

        authorities = authoritiesService.saveAuthority(authorities);
        newUserRole.addAuthority(authorities);
        userRole = roleService.saveRole(newUserRole);

      }

      User myUser =
              User.builder()
                      .name(user.getName())
                      .username(user.getUsername())
                      .email(user.getEmail())
                      .password(passwordEncoder.encode(user.getPassword()))
//                      .myRoles(Collections.singleton(userRole))
                      .isAccountNonExpired(true)
                      .isAccountNonLocked(true)
                      .isCredentialsNonExpired(true)
                      .isEnabled(true)
                      .build();
      myUser.addRoleToUser(userRole);
      return userRepo.save(myUser);
    }

  }

  @Override
  public JwtToken signInUser(SignInDTO signInDTO) {
    Authentication auth =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                signInDTO.getUsername(), signInDTO.getPassword()));

    String token = jwtTokenProvider.createJwtToken(auth);
    return new JwtToken(token);
  }

  @Override
  public User findByName(String name) {
    return userRepo.findByUsername(name).orElseThrow(()-> new ResourceNotFoundException("User", "name", name));
  }

  @Override
  public long countUsers() {
    return userRepo.count();
  }

  @Override
  public User saveUser(User user) {
    return userRepo.save(user);
  }

  @Override
  public User createUser(User user) {
    Set<Roles> roleSet = user.getRoles();
    User myUser =
            User.builder()
                    .name(user.getName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .roles(roleSet)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build();



    user.getRoles().forEach(role -> role.addUser(myUser));

    return userRepo.save(myUser);
  }

  @Override
  public PageableUserDTO getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNo - 1,pageSize,sort);
    Page<User> page = userRepo.findAll(pageable);

    List<User> users = page.stream().collect(Collectors.toList());

    return PageableUserDTO.builder()
            .responseDTOList(users)
            .pageSize(page.getSize())
            .pageNo(page.getNumber() + 1)
            .totalPages(page.getTotalPages())
            .totalElements(page.getTotalElements())
            .isLast(page.isLast())
            .build();
  }

  @Override
  public User getUserById(long id) {
    return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  }

  @Override
  public User updateUser(long id, User user) {
    return null;
  }

  @Override
  public void deleteUser(long id) {

  }

}
