package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignInDTO {
  private String username;
  private String password;
}
