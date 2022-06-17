package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import com.abdiahmed.springbootblog.repository.RoleRepository;
import com.abdiahmed.springbootblog.service.interfaces.RoleService;
import com.abdiahmed.springbootblog.service.mapper.RoleMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired RoleRepository roleRepo;
  @Autowired AuthoritiesServiceImpl authoritiesService;

  @Autowired RoleMapperImpl roleMapper;

  @Override
  public List<Role> getAllRoles() {
    return roleRepo.findAll();
  }

  @Override
  public Role getRoleById(long id) {
    return roleRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Role", "Id", id));
  }

  @Override
  public Role getRoleByName(String name) {
    return roleRepo
        .findByName(name)
        .orElseThrow(() -> new ResourceNotFoundException("Role", "name", name));
  }

  @Override
  public boolean roleDoesNotExist(String name) {
    return roleRepo.findByName(name).isEmpty();
  }

  @Override
  public Role saveRole(Role role) {
    return roleRepo.save(role);
  }

  @Override
  public Role createRole(String role) throws ResourceExist {
    if (roleRepo.findByName(role).isPresent()) {
      throw new ResourceExist("Role already exists");
    }
    Role newRole = new Role();
    newRole.setName(role);
    return roleRepo.save(newRole);
  }

  @Override
  public Role updateRole(long id, String role) {
    Role foundRole = getRoleById(id);
    foundRole.setName(role);
    return roleRepo.save(foundRole);
  }

  @Override
  public Role deleteRole(long id) {
    Role deleteRole = getRoleById(id);
    roleRepo.delete(deleteRole);
    return deleteRole;
  }

  @Override
  public RoleResponseDTO deleteRoleAuthority(long roleId, long authoritiesId) {
    Role role = getRoleById(roleId);
    Authorities deleteAuthority = authoritiesService.getAuthorityById(authoritiesId);
    List<Authorities> collect =
        role.getAuthorities().stream()
            .filter(authorities -> authorities == deleteAuthority)
            .collect(Collectors.toList());
    Authorities authorities = collect.get(0);
    if (authorities != null) {
      role.removeAuthority(authorities);
      roleRepo.save(role);
    }

    return roleMapper.mapToDTO(role);
  }

  public Role addAuthorityToRole(long roleId, long authorityId) {
    Authorities authority = authoritiesService.getAuthorityById(authorityId);
    Role role = getRoleById(roleId);
    role.addAuthority(authority);
    authority.addRoleToAuthorities(role);
    authoritiesService.addRole(authorityId, role);
    return roleRepo.save(role);
  }
}
