package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthoritiesDTO {
  @NotEmpty
  @Size(min = 2, message = "name should have at lest 2 characters long")
  private String name;
}
