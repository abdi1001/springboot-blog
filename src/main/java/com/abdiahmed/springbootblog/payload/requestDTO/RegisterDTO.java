package com.abdiahmed.springbootblog.payload.requestDTO;

import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
  @NotBlank
//  @Size(min = 2, message = "name should have at lest 2 characters long")
  private String name;
  @NotEmpty
  @Size(min = 2, message = "username should have at lest 2 characters long")
  private String username;
  @Email
  @NotEmpty
  private String email;
  @NotEmpty
  @Size(min = 4, message = "password should have at lest 2 characters long")
  private String password;
}
