package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface AuthoritiesMapper {
  Authorities mapToEntity(AuthoritiesResponseDTO authoritiesResponseDTO);

  AuthoritiesResponseDTO mapToDTO(Authorities authorities);
  //    AuthoritiesResponseDTO mapToDTO(CreateAuthoritiesDTO authorities);
  Authorities mapToEntity(CreateAuthoritiesDTO authoritiesDTO);

  Set<Authorities> mapToEntity(Set<CreateAuthoritiesDTO> authoritiesDTO);
}
