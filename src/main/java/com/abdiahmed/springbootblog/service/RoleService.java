package com.abdiahmed.springbootblog.service;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.MyRoles;

import java.util.List;

public interface RoleService {
    List<MyRoles> getAllRoles();

    MyRoles getRoleById(long id);

    MyRoles createRole(String role) throws ResourceExist;

    MyRoles updateRole(long id, String role);

    MyRoles deleteRole(long id);

    MyRoles addAuthorityToRole(long id, long authorityId);

    MyRoles getRoleByName(String name);
    boolean roleDoesNotExist(String name);

    MyRoles saveRole(MyRoles roles);

}

