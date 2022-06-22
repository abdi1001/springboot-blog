package com.abdiahmed.springbootblog.controller;

import com.abdiahmed.springbootblog.error.UpdateResourceException;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import com.abdiahmed.springbootblog.service.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role/{roleId}/authorities")
public class AuthoritiesController {

  @Autowired RoleService roleService;
  //
  @PostMapping()
  public ResponseEntity<RoleResponseDTO> addAuthorityToRole(
      @PathVariable long roleId, @RequestBody CreateAuthoritiesDTO createAuthoritiesDTO) {
    RoleResponseDTO roleResponseDTO = roleService.addAuthorityToRole(roleId, createAuthoritiesDTO);
    return new ResponseEntity<>(roleResponseDTO, HttpStatus.OK);
  }

  @PutMapping("/{authorityId}")
  public ResponseEntity<RoleResponseDTO> updateAuthorityOnRole(
      @PathVariable long roleId,
      @PathVariable long authorityId,
      @RequestBody AuthoritiesResponseDTO authoritiesResponseDTO)
      throws UpdateResourceException {
    RoleResponseDTO roleResponseDTO =
        roleService.updateAuthorityOnRole(roleId, authorityId, authoritiesResponseDTO);
    return new ResponseEntity<>(roleResponseDTO, HttpStatus.OK);
  }

  @DeleteMapping("/{authorityId}")
  public ResponseEntity<RoleResponseDTO> deleteAuthorityFromRole(
      @PathVariable long roleId, @PathVariable long authorityId) throws UpdateResourceException {
    RoleResponseDTO roleResponseDTO = roleService.deleteAuthorityFromRole(roleId, authorityId);
    return new ResponseEntity<>(roleResponseDTO, HttpStatus.OK);
  }
}
