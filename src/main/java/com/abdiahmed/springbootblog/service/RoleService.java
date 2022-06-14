package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Roles;

import java.util.List;

public interface RoleService {
    List<Roles> getAllRoles();

    Roles getRoleById(long id);

    Roles createRole(String role) throws ResourceExist;

    Roles updateRole(long id, String role);

    Roles deleteRole(long id);

    Roles addAuthorityToRole(long id, long authorityId);

    Roles getRoleByName(String name);
    boolean roleDoesNotExist(String name);

    Roles saveRole(Roles roles);

}

