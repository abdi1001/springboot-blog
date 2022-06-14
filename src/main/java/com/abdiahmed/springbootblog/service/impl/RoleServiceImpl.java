package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.MyAuthorities;
import com.abdiahmed.springbootblog.model.MyRoles;
import com.abdiahmed.springbootblog.repository.MyRolesRepository;
import com.abdiahmed.springbootblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired MyRolesRepository roleRepo;
  @Autowired
  AuthoritiesServiceImpl authoritiesService;

  @Override
  public List<MyRoles> getAllRoles() {
    return roleRepo.findAll();
  }

  @Override
  public MyRoles getRoleById(long id) {
    return roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));
  }

  @Override
  public MyRoles getRoleByName(String name) {
    return roleRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
  }

  @Override
  public boolean roleDoesNotExist(String name) {
    return roleRepo.findByName(name).isEmpty();
  }

  @Override
  public MyRoles saveRole(MyRoles role) {
    return roleRepo.save(role);
  }

  @Override
  public MyRoles createRole(String role) throws ResourceExist {
    if(roleRepo.findByName(role).isPresent()) {
      throw new ResourceExist("Role already exists");
    }
    MyRoles newRole = new MyRoles();
    newRole.setName(role);
   return roleRepo.save(newRole);
  }

  @Override
  public MyRoles updateRole(long id, String role) {
    MyRoles foundRole = getRoleById(id);
    foundRole.setName(role);
   return roleRepo.save(foundRole);
  }

  @Override
  public MyRoles deleteRole(long id) {
    MyRoles deleteRole = getRoleById(id);
    roleRepo.delete(deleteRole);
    return  deleteRole;
  }

  public MyRoles addAuthorityToRole(long roleId, long authorityId) {
    MyAuthorities authority = authoritiesService.getAuthorityById(authorityId);
    MyRoles role = getRoleById(roleId);
    role.addAuthority(authority);
    authority.setRoles(role);
    authoritiesService.addRole(authorityId, role);
    return roleRepo.save(role);
  }
}
