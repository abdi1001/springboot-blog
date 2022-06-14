package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.MyRoles;
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
    public ResponseEntity<List<MyRoles>> getAllRoles(){
        List<MyRoles> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyRoles> getRoleById(@PathVariable long id){
        MyRoles role = roleService.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping("/{roleId}/{authorityId}")
    public ResponseEntity<MyRoles> getRoleById(@PathVariable long roleId, @PathVariable long authorityId){
        MyRoles role = roleService.addAuthorityToRole(roleId,authorityId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MyRoles> createRole(@RequestBody MyRoles role) throws ResourceExist {
        MyRoles newRole = roleService.createRole(role.getName());
        return new ResponseEntity<>(newRole, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyRoles> updateRole(@PathVariable long id, @RequestBody MyRoles role){
        MyRoles updatedRole = roleService.updateRole(id, role.getName());
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MyRoles> deleteRole(@PathVariable long id){
        MyRoles updatedRole = roleService.deleteRole(id);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }
}
