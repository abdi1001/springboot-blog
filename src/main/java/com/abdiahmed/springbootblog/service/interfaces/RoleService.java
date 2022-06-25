package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.UpdateResourceException;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateRoleDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

public interface RoleService {
  List<Role> getAllRoles();

  Role getRoleById(long id);

  boolean roleDoesNotExists(String name);

  Role createRole(String role) throws ResourceExist;

  Role updateRole(long id, CreateRoleDTO role);

  Role deleteRole(long id);

  Role getRoleByName(String name);

//  RoleResponseDTO addAuthorityToRole(long roleId, CreateAuthoritiesDTO createAuthoritiesDTO);

  @Transactional
  RoleResponseDTO addAuthorityToRole(
          long roleId, Set<CreateAuthoritiesDTO> createAuthoritiesDTO);

  @Transactional
  RoleResponseDTO deleteAuthorityFromRole(
          long roleId, Set<CreateAuthoritiesDTO> createAuthoritiesDTO);

  RoleResponseDTO addAuthorityToRoleById(
          long roleId, long authorityId);


  RoleResponseDTO addAuthoritiesToRole(
          long roleId, Set<CreateAuthoritiesDTO> createAuthoritiesDTO);

  RoleResponseDTO updateAuthorityOnRole(
      long roleId, long authorityId, AuthoritiesResponseDTO authoritiesResponseDTO)
      throws UpdateResourceException;

  RoleResponseDTO deleteAuthorityFromRole(long roleId, long authorityId);
}
