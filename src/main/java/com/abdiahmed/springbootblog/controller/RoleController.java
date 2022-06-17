package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import com.abdiahmed.springbootblog.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRoleById(@PathVariable long id){
        Role role = roleService.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping("/{roleId}/{authorityId}")
    public ResponseEntity<Role> getRoleById(@PathVariable long roleId, @PathVariable long authorityId){
        Role role = roleService.addAuthorityToRole(roleId,authorityId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Role> createRole(@RequestBody Role role) throws ResourceExist {
        Role newRole = roleService.createRole(role.getName());
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable long id, @RequestBody Role role){
        Role updatedRole = roleService.updateRole(id, role.getName());
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> deleteRole(@PathVariable long id){
        Role updatedRole = roleService.deleteRole(id);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}/{authorityId}")
    public ResponseEntity<RoleResponseDTO> deleteRoleAuthority(@PathVariable long roleId,@PathVariable long authorityId){
        RoleResponseDTO updatedRole = roleService.deleteRoleAuthority(roleId, authorityId);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }
}
