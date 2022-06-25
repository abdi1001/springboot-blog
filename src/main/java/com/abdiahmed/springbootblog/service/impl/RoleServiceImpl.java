package com.abdiahmed.springbootblog.service.impl;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.ResourceNotFoundException;
import com.abdiahmed.springbootblog.error.UpdateResourceException;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateRoleDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import com.abdiahmed.springbootblog.repository.RoleRepository;
import com.abdiahmed.springbootblog.service.interfaces.RoleService;
import com.abdiahmed.springbootblog.service.mapper.AuthoritiesMapperImpl;
import com.abdiahmed.springbootblog.service.mapper.RoleMapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
  @Autowired RoleRepository roleRepo;
  @Autowired AuthoritiesServiceImpl authoritiesService;

  @Autowired RoleMapperImpl roleMapper;
  @Autowired AuthoritiesMapperImpl authoritiesMapper;

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
  public boolean roleDoesNotExists(String name) {
    return roleRepo.findByName(name).isEmpty();
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
  public Role updateRole(long id, CreateRoleDTO role)  {
    Role foundRole = getRoleById(id);
    foundRole.setName(role.getName());
    return roleRepo.save(foundRole);
  }

  @Override
  public Role deleteRole(long id) {
    Role deleteRole = getRoleById(id);

    roleRepo.delete(deleteRole);
    return deleteRole;
  }


  @Override
  @Transactional
  public RoleResponseDTO addAuthorityToRole(
          long roleId, Set<CreateAuthoritiesDTO> createAuthoritiesDTO) {
    Role role = getRoleById(roleId);
    Set<Authorities> authoritiesSet = authoritiesService.confirmAuthorityExistAndReturn(createAuthoritiesDTO);

    authoritiesSet.forEach(authority -> role.getAuthorities().add(authority));
    roleRepo.save(role);
    return roleMapper.mapToDTO(role);
  }

  @Override
  @Transactional
  public RoleResponseDTO deleteAuthorityFromRole(
          long roleId, Set<CreateAuthoritiesDTO> createAuthoritiesDTO) {
    Role role = getRoleById(roleId);
    Set<Authorities> authoritiesSet = authoritiesService.confirmAuthorityExistAndReturn(createAuthoritiesDTO);



    authoritiesSet.forEach(authority -> role.getAuthorities().remove(authority));
    roleRepo.save(role);
    return roleMapper.mapToDTO(role);
  }

  @Override
  public RoleResponseDTO addAuthorityToRoleById(
          long roleId, long authorityId) {
    Role role = getRoleById(roleId);
    Authorities authority = authoritiesService.findById(authorityId);

    role.addAuthority(authority);
    roleRepo.save(role);
    return roleMapper.mapToDTO(role);
  }



  @Override
  public RoleResponseDTO addAuthoritiesToRole(
          long roleId, Set<CreateAuthoritiesDTO> createAuthoritiesDTO) {
    Role role = getRoleById(roleId);
    role.getAuthorities()
        .forEach(
            authorities -> {
              String authorityName = authorities.getName();
              boolean authorityExists =
                  createAuthoritiesDTO.stream()
                      .anyMatch(
                          authoritiesDTO ->
                              authoritiesDTO.getName().equalsIgnoreCase(authorityName));
              if (authorityExists)
                throw new ResourceExist("authority " + authorityName + "already exists");
            });
    Set<Authorities> authorities = authoritiesMapper.mapToEntity(createAuthoritiesDTO);
    authorities.forEach(role::addAuthority);
    roleRepo.save(role);
    return roleMapper.mapToDTO(role);
  }

  @Override
  public RoleResponseDTO updateAuthorityOnRole(
      long roleId, long authorityId, AuthoritiesResponseDTO authoritiesResponseDTO)
      throws UpdateResourceException {
    Role role = getRoleById(roleId);
    Authorities authority = validateAuthority(role, authorityId);
    if (authoritiesResponseDTO.getId() != authorityId) {
      throw new UpdateResourceException(authorityId, authoritiesResponseDTO.getId());
    }
    authority.setName(authoritiesResponseDTO.getName());
    return roleMapper.mapToDTO(roleRepo.save(role));
  }

  @Override
  public RoleResponseDTO deleteAuthorityFromRole(long roleId, long authorityId) {
    Role role = getRoleById(roleId);
    Authorities authority = validateAuthority(role, authorityId);
    role.removeAuthority(authority);
    roleRepo.save(role);
    return roleMapper.mapToDTO(role);
  }

  private Authorities validateAuthority(Role role, long authorityId) {
    return role.getAuthorities().stream()
        .filter(authority -> authority.getId() == authorityId)
        .findFirst()
        .orElseThrow(() -> new ResourceNotFoundException("This role", "authority Id", authorityId));
  }
}
