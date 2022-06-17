package com.abdiahmed.springbootblog.service.interfaces;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;

import java.util.List;

public interface RoleService {
    List<Role> getAllRoles();

    Role getRoleById(long id);

    Role createRole(String role) throws ResourceExist;

    Role updateRole(long id, String role);

    Role deleteRole(long id);

    RoleResponseDTO deleteRoleAuthority(long roleId, long authoritiesId);

    Role addAuthorityToRole(long id, long authorityId);

    Role getRoleByName(String name);
    boolean roleDoesNotExist(String name);

    Role saveRole(Role role);

}

