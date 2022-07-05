package com.abdiahmed.springbootblog.integration;

import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.payload.requestDTO.CreateAuthoritiesDTO;
import com.abdiahmed.springbootblog.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class RoleControllerIntTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private RoleRepository roleRepository;

  @Autowired private ObjectMapper objectMapper;
  private Role role;

  @BeforeEach
  void setup() {
    roleRepository.deleteAll();
    role = new Role();
    role.setName("ROLE:TEST");
    roleRepository.save(role);
  }

  @Test
  @DisplayName("Junit test for ")
  @WithMockUser(username = "admin",roles = {"ADMIN","USER"})
  public void givenRoleObject_whenPostRole_thenSaveAndReturnRole() throws Exception {

    // given
    Role newRole = new Role();
    newRole.setName("ROLE:NEW");

    // when
    ResultActions response =
        mockMvc.perform(
            post("/api/v1/role")

                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(newRole)));

    // then
    response.andDo(print());
  }

  @Test
  @DisplayName("Junit test for ")
  @WithMockUser(username = "admin",roles = {"ADMIN","USER"})
  public void givenRoleId_whenGetById_thenReturnRole() throws Exception {

    // given
    Long roleId1 = role.getId();

    // when
    ResultActions response =
            mockMvc.perform(
                    get("/api/v1/role/" + roleId1));

    // then
    response.andDo(print()).andExpect(status().isOk());
  }

  @Test
  @DisplayName("Junit test for ")
  @WithMockUser(username = "admin",roles = {"ADMIN","USER"})
  public void givenListOfRoles_whenGetAllRoles_thenReturnRoles() throws Exception {

    // given
    List<Role> roleList = new ArrayList<>();
    roleList.add(Role.builder().name("ROLE:TEST1").build());
    roleList.add(Role.builder().name("ROLE:TEST2").build());
    roleRepository.saveAll(roleList);

    // when
    ResultActions response =
            mockMvc.perform(
                    get("/api/v1/role/"));

    // then
    response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", CoreMatchers.is(3)));
  }

  @Test
  @DisplayName("Junit test for ")
  @WithMockUser(username = "admin",roles = {"ADMIN","USER"})
  public void givenRoleWithAuthority_whenSave_thenReturnRoleWithAuthority() throws Exception {

    // given
    Long roleId = role.getId();

    Set<CreateAuthoritiesDTO> authSet = new HashSet();

    CreateAuthoritiesDTO auth1 = CreateAuthoritiesDTO.builder().name("auth1").build();
    CreateAuthoritiesDTO auth2 = CreateAuthoritiesDTO.builder().name("auth2").build();
    CreateAuthoritiesDTO auth3 = CreateAuthoritiesDTO.builder().name("auth3").build();
    authSet.add(auth1);
    authSet.add(auth2);
    authSet.add(auth3);


    // when
    ResultActions response =
            mockMvc.perform(
                    post("/api/v1/role/" + roleId +"/authorities")

                            .contentType(MediaType.APPLICATION_JSON_VALUE)
                            .content(objectMapper.writeValueAsString(authSet)));

    // then
    response.andDo(print());
  }
}
