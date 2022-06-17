package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.AuthoritiesResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthoritiesMapper {
    Authorities mapToEntity(AuthoritiesResponseDTO authoritiesResponseDTO);
    AuthoritiesResponseDTO mapToDTO(Authorities authorities);
    AuthoritiesResponseDTO mapToDTO(CreateAuthoritiesDTO authorities);
}
