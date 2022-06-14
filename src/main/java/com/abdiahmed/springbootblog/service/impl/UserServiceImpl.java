package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.MyAuthorities;
import com.abdiahmed.springbootblog.model.MyRoles;
import com.abdiahmed.springbootblog.model.MyUser;
import com.abdiahmed.springbootblog.payload.requestDTO.RegisterDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.SignInDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.JwtToken;
import com.abdiahmed.springbootblog.payload.responseDTO.PageableUserDTO;
import com.abdiahmed.springbootblog.repository.MyUserRepository;
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
  MyUserRepository userRepo;
  RoleServiceImpl roleService;
  AuthoritiesServiceImpl authoritiesService;
  BCryptPasswordEncoder passwordEncoder;

  AuthenticationManager authenticationManager;

  JwtTokenProvider jwtTokenProvider;

  public UserServiceImpl(
      MyUserRepository userRepo,
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
  public MyUser registerUser(RegisterDTO user) throws ResourceExist {

    if (userRepo.count() == 0) {
      if(roleService.roleDoesNotExist("ROLE_ADMIN")){

      }
      MyRoles adminRole = roleService.createRole("ROLE_ADMIN");

      List<MyAuthorities> myAuthoritiesList = new ArrayList<>();

      MyAuthorities authorities1 = authoritiesService.createAuthority("User:Read");
      authoritiesService.addRole(authorities1.getId(),adminRole);

      MyAuthorities authorities2 = authoritiesService.createAuthority("User:Create");
      authoritiesService.addRole(authorities2.getId(),adminRole);

      MyAuthorities authorities3 = authoritiesService.createAuthority("User:Update");
      authoritiesService.addRole(authorities3.getId(),adminRole);

      MyAuthorities authorities4 = authoritiesService.createAuthority("User:Delete");
      authoritiesService.addRole(authorities4.getId(),adminRole);

      myAuthoritiesList.add(authorities1);
      myAuthoritiesList.add(authorities2);
      myAuthoritiesList.add(authorities3);
      myAuthoritiesList.add(authorities4);

      List<MyAuthorities> mySavedAuthorities = authoritiesService.SaveAuthoritiesList(myAuthoritiesList);

      for(MyAuthorities authority : mySavedAuthorities) {
        adminRole.addAuthority(authority);
      }
      MyRoles savedRole = roleService.saveRole(adminRole);

      MyUser myUser =
              MyUser.builder()
                      .name(user.getName())
                      .username(user.getUsername())
                      .email(user.getEmail())
                      .password(passwordEncoder.encode(user.getPassword()))
                      .myRoles(Collections.singleton(savedRole))
                      .isAccountNonExpired(true)
                      .isAccountNonLocked(true)
                      .isCredentialsNonExpired(true)
                      .isEnabled(true)
                      .build();
      return userRepo.save(myUser);

    } else {
      MyRoles userRole = null;
      if(roleService.roleDoesNotExist("ROLE_USER")) {
        MyRoles newUserRole = roleService.createRole("ROLE_USER");

        MyAuthorities authorities = authoritiesService.findAuthorityByName("User:Read");
        authorities.setRoles(newUserRole);

        authorities = authoritiesService.saveAuthority(authorities);
        newUserRole.addAuthority(authorities);
        userRole = roleService.saveRole(newUserRole);

      }

      MyUser myUser =
              MyUser.builder()
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
  public MyUser findByName(String name) {
    return userRepo.findByUsername(name).orElseThrow(()-> new ResourceNotFoundException("User", "name", name));
  }

  @Override
  public long countUsers() {
    return userRepo.count();
  }

  @Override
  public MyUser saveUser(MyUser user) {
    return userRepo.save(user);
  }

  @Override
  public MyUser createUser(MyUser user) {
    Set<MyRoles> roleSet = user.getMyRoles();
    MyUser myUser =
            MyUser.builder()
                    .name(user.getName())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(passwordEncoder.encode(user.getPassword()))
                    .myRoles(roleSet)
                    .isAccountNonExpired(true)
                    .isAccountNonLocked(true)
                    .isCredentialsNonExpired(true)
                    .isEnabled(true)
                    .build();



    user.getMyRoles().forEach(role -> role.addUser(myUser));

    return userRepo.save(myUser);
  }

  @Override
  public PageableUserDTO getAllUsers(int pageNo, int pageSize, String sortBy, String sortDir) {
    Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(pageNo - 1,pageSize,sort);
    Page<MyUser> page = userRepo.findAll(pageable);

    List<MyUser> users = page.stream().collect(Collectors.toList());

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
  public MyUser getUserById(long id) {
    return userRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
  }

  @Override
  public MyUser updateUser(long id, MyUser user) {
    return null;
  }

  @Override
  public void deleteUser(long id) {

  }

}
