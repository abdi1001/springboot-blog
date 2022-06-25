package com.abdiahmed.springbootblog.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleResponseDTO {

  private Long id;
  private String name;
  private Set<AuthoritiesResponseDTO> authorities = new HashSet<>();
}
