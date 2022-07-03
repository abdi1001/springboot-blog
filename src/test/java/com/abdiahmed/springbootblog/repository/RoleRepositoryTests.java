package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Authorities;
import com.abdiahmed.springbootblog.model.Role;
import com.abdiahmed.springbootblog.model.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleRepositoryTests {


    @Autowired
    RoleRepository roleRepository;

    private Role role;

    @BeforeEach
    public void setup(){
        role = new Role();
        role.setName("ROLE_USER");

    }

    //BDD style -- given_when_then
    @Test
    @DisplayName("Junit test for save role")
    public void givenRoleObject_whenSave_thenReturnSavedRole() {

        //given


        //when
        Role savedRole = roleRepository.save(role);

        //then

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isGreaterThan(0);

    }

    @Test
    @DisplayName("Junit test for save role With Authority")
    public void givenRoleObjectWithAuthority_whenSave_thenReturnSavedRole() {

        //given
        Authorities authorities = new Authorities();
        authorities.setName("USER:READ");
        authorities.addRoleToAuthorities(role);
        role.addAuthority(authorities);


        //when
        Role savedRole = roleRepository.save(role);

        //then

        assertThat(savedRole).isNotNull();
        assertThat(savedRole.getId()).isGreaterThan(0);
        assertThat(savedRole.getAuthorities().size()).isEqualTo(1);
        assertThat(savedRole.getAuthorities().contains(authorities)).isEqualTo(true);

    }


    @Test
    @DisplayName("Junit test for find all authorities")
    public void givenRoleList_whenFindAll_thenReturnRoleList() {

        //given - precondition or setup

        Role role1 = new Role();
        role1.setName("USER:UPDATE");

        roleRepository.save(role);
        roleRepository.save(role1);


        //when - action or the behaviour that we are testing
        List<Role> roleList = roleRepository.findAll();

        //then - veryfy the output
        assertThat(roleList).isNotNull();
        assertThat(roleList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Junit test for save all authorities")
    public void givenRoleList_whenSaveAll_thenReturnRoleList() {

        //given - precondition or setup

        Role role1 = new Role();
        role1.setName("USER:UPDATE");

        List<Role> roleList = List.of(role,role1);


        //when - action or the behaviour that we are testing
        List<Role> foundRoleList = roleRepository.saveAll(roleList);

        //then - veryfy the output
        assertThat(foundRoleList).isNotNull();
        assertThat(foundRoleList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Junit test for finding role by id")
    public void givenRoleObject_whenFindById_thenReturnRoleObject() {

        //given - precondition or setup

        Role savedRole = roleRepository.save(role);


        //when - action or the behaviour that we are testing
        Role roleInDb = roleRepository.findById(savedRole.getId()).get();


        //then - veryfy the output
        assertThat(roleInDb).isNotNull();

    }

    @Test
    @DisplayName("Junit test for finding role by name")
    public void givenRoleName_whenFindByName_thenReturnRoleObject() {

        //given - precondition or setup
        roleRepository.save(role);

        //when - action or the behaviour that we are testing
        Role foundRole = roleRepository.findByName(role.getName()).get();


        //then - veryfy the output
        assertThat(foundRole).isNotNull();
    }

    @Test
    @DisplayName("Junit test for deleting role with role object")
    public void givenRoleObject_whenDelete_thenDeleteRole() {

        //given - precondition or setup
        roleRepository.save(role);


        //when - action or the behaviour that we are testing
        roleRepository.delete(role);
        Optional<Role> roleOptional = roleRepository.findById(role.getId());


        //then - veryfy the output
        assertThat(roleOptional).isEmpty();

    }

    @Test
    @DisplayName("Junit test for deleting role with role Id")
    public void givenRoleId_whenDelete_thenDeleteRole() {

        //given - precondition or setup
        Role authorities = new Role();
        authorities.setName("ROLE_USER");

        roleRepository.save(authorities);


        //when - action or the behaviour that we are testing
        roleRepository.deleteById(authorities.getId());
        Optional<Role> roleOptional = roleRepository.findById(authorities.getId());


        //then - veryfy the output
        assertThat(roleOptional).isEmpty();

    }
}
