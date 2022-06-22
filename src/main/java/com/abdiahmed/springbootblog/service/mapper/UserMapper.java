package com.abdiahmed.springbootblog.service.mapper;

import com.abdiahmed.springbootblog.model.User;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateUserDTO;
import com.abdiahmed.springbootblog.payload.responseDTO.UserResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
  User mapToEntity(UserResponseDTO userResponseDTO);

  @Mapping(source = "user.role", target = "role")
  UserResponseDTO mapToDTO(User user);
//  @Mapping(source = "user.role", target = "role")
//  UserResponseDTO mapToDTO(List<User> user);

  UserResponseDTO mapToDTO(CreateUserDTO createUserDTO);
}
