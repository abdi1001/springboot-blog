package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Roles;
import com.abdiahmed.springbootblog.service.RoleService;
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
    public ResponseEntity<List<Roles>> getAllRoles(){
        List<Roles> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Roles> getRoleById(@PathVariable long id){
        Roles role = roleService.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping("/{roleId}/{authorityId}")
    public ResponseEntity<Roles> getRoleById(@PathVariable long roleId, @PathVariable long authorityId){
        Roles role = roleService.addAuthorityToRole(roleId,authorityId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Roles> createRole(@RequestBody Roles role) throws ResourceExist {
        Roles newRole = roleService.createRole(role.getName());
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Roles> updateRole(@PathVariable long id, @RequestBody Roles role){
        Roles updatedRole = roleService.updateRole(id, role.getName());
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Roles> deleteRole(@PathVariable long id){
        Roles updatedRole = roleService.deleteRole(id);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }
}
