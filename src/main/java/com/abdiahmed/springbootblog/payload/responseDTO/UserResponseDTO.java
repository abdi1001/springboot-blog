package com.abdiahmed.springbootblog.payload.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {

  private long id;
  private String name;
  private String email;
  private Set<RoleResponseDTO> role;
  private boolean enabled;
  private boolean accountNonExpired;
  private boolean credentialsNonExpired;
  private boolean accountNonLocked;
}
