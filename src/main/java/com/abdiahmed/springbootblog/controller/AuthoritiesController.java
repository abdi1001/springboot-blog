package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.ResourceExist;
import com.abdiahmed.springbootblog.model.MyAuthorities;
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
    public ResponseEntity<List<MyAuthorities>> getAllAuthorities(){
        List<MyAuthorities> authoritys = authrepo.getAllAuthorities();
        return new ResponseEntity<>(authoritys, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MyAuthorities> getAuthorityById(@PathVariable long id){
        MyAuthorities authority = authrepo.getAuthorityById(id);
        return new ResponseEntity<>(authority, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<MyAuthorities> createAuthority(@RequestBody MyAuthorities authority) throws ResourceExist {
        MyAuthorities newAuthority = authrepo.createAuthority(authority.getName());
        return new ResponseEntity<>(newAuthority, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MyAuthorities> updateAuthority(@PathVariable long id, @RequestBody MyAuthorities authority){
        MyAuthorities updatedAuthority = authrepo.updateAuthority(id, authority.getName());
        return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MyAuthorities> deleteAuthority(@PathVariable long id){
        MyAuthorities updatedAuthority = authrepo.deleteAuthority(id);
        return new ResponseEntity<>(updatedAuthority, HttpStatus.OK);
    }
}
