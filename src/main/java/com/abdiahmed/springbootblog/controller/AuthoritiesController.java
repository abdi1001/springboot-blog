package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.service.impl.AuthoritiesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authorities")
public class AuthoritiesController {

    @Autowired
    AuthoritiesServiceImpl authrepo;

    @GetMapping()
    public ResponseEntity<List<Authorities>> getAllAuthorities(){
        List<Authorities> authoritys = authrepo.getAllAuthorities();
        return new ResponseEntity<>(authoritys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authorities> getAuthorityById(@PathVariable long id){
        Authorities authority = authrepo.getAuthorityById(id);
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Authorities> createAuthority(@RequestBody Authorities authority) throws ResourceExist {
        Authorities newAuthority = authrepo.createAuthority(authority.getName());
        return new ResponseEntity<>(newAuthority, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Authorities> updateAuthority(@PathVariable long id, @RequestBody Authorities authority){
        Authorities updatedAuthority = authrepo.updateAuthority(id, authority.getName());
        return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Authorities> deleteAuthority(@PathVariable long id){
        Authorities updatedAuthority = authrepo.deleteAuthority(id);
        return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);
    }
}
