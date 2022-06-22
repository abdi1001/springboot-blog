package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateRoleDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
  Role mapToEntity(RoleResponseDTO roleResponseDTO);

  RoleResponseDTO mapToDTO(Role role);

  @Mapping(source = "name", target = "name")
  RoleResponseDTO mapToDTO(CreateRoleDTO role);
}
