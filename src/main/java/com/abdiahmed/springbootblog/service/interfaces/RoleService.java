package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.error.UpdateResourceException;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;

import java.util.List;

public interface RoleService {
  List<Role> getAllRoles();

  Role getRoleById(long id);

  boolean roleDoesNotExists(String name);

  Role createRole(String role) throws ResourceExist;

  Role updateRole(long id, String role);

  Role deleteRole(long id);

  Role getRoleByName(String name);

  RoleResponseDTO addAuthorityToRole(long roleId, CreateAuthoritiesDTO createAuthoritiesDTO);

  RoleResponseDTO addAuthoritiesToRole(
      long roleId, List<CreateAuthoritiesDTO> createAuthoritiesDTO);

  RoleResponseDTO updateAuthorityOnRole(
      long roleId, long authorityId, AuthoritiesResponseDTO authoritiesResponseDTO)
      throws UpdateResourceException;

  RoleResponseDTO deleteAuthorityFromRole(long roleId, long authorityId);
}
