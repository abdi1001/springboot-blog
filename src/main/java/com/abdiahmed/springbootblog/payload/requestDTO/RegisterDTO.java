package com.abdiahmed.springbootblog.payload.requestDTO;

import com.abdiahmed.springbootblog.payload.responseDTO.RoleResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDTO {
    private String name;
    private String username;
    private String email;
    private String password;
    private Set<RoleResponseDTO> roles;
}
