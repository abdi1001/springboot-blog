package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRoleDTO {

  @NotEmpty
  @Size(min = 2, message = "role should have at lest 2 characters long")
  private String name;
}
