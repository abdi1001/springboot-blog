package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.model.Roles;
import com.abdiahmed.springbootblog.repository.RolesRepository;
import com.abdiahmed.springbootblog.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired
  RolesRepository roleRepo;
  @Autowired
  AuthoritiesServiceImpl authoritiesService;

  @Override
  public List<Roles> getAllRoles() {
    return roleRepo.findAll();
  }

  @Override
  public Roles getRoleById(long id) {
    return roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));
  }

  @Override
  public Roles getRoleByName(String name) {
    return roleRepo.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
  }

  @Override
  public boolean roleDoesNotExist(String name) {
    return roleRepo.findByName(name).isEmpty();
  }

  @Override
  public Roles saveRole(Roles role) {
    return roleRepo.save(role);
  }

  @Override
  public Roles createRole(String role) throws ResourceExist {
    if(roleRepo.findByName(role).isPresent()) {
      throw new ResourceExist("Role already exists");
    }
    Roles newRole = new Roles();
    newRole.setName(role);
   return roleRepo.save(newRole);
  }

  @Override
  public Roles updateRole(long id, String role) {
    Roles foundRole = getRoleById(id);
    foundRole.setName(role);
   return roleRepo.save(foundRole);
  }

  @Override
  public Roles deleteRole(long id) {
    Roles deleteRole = getRoleById(id);
    roleRepo.delete(deleteRole);
    return  deleteRole;
  }

  public Roles addAuthorityToRole(long roleId, long authorityId) {
    Authorities authority = authoritiesService.getAuthorityById(authorityId);
    Roles role = getRoleById(roleId);
    role.addAuthority(authority);
    authority.setRoles(role);
    authoritiesService.addRole(authorityId, role);
    return roleRepo.save(role);
  }
}
