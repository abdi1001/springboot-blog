package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {

  @NotEmpty
  @Size(min = 2, message = "name should have at lest 2 characters long")
  private String name;

  @NotEmpty
  @Size(min = 2, message = "username should have at lest 2 characters long")
  private String username;
  @Email
  private String email;
  @NotEmpty
  @Size(min = 5, message = "user name should have at lest 2 characters long")
  private String password;
  private Set<CreateRoleDTO> roleName;
}
