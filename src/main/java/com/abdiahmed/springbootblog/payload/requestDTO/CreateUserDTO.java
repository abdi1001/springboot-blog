package com.abdiahmed.springbootblog.payload.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserDTO {
    private String username;
    private String email;
    private String password;
    private Set<CreateRoleDTO> roleName;
}
