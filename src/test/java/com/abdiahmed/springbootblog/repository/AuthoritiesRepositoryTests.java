package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Authorities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class AuthoritiesRepositoryTests {

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    private Authorities authority;

    @BeforeEach
    public void setup(){
        authority = new Authorities();
        authority.setName("USER:READ");

    }

    //BDD style -- given_when_then
    @Test
    @DisplayName("Junit test for save authority")
    public void givenAuthorityObject_whenSave_thenReturnSavedAuthority() {

        //given


        //when
        Authorities savedAuthority = authoritiesRepository.save(authority);

        //then

        assertThat(savedAuthority).isNotNull();
        assertThat(savedAuthority.getId()).isGreaterThan(0);

    }


    @Test
    @DisplayName("Junit test for find all authorities")
    public void givenAuthoritiesList_whenFindAll_thenReturnAuthoritiesList() {

        //given - precondition or setup

        Authorities authorities1 = new Authorities();
        authorities1.setName("USER:UPDATE");

        authoritiesRepository.save(authority);
        authoritiesRepository.save(authorities1);


        //when - action or the behaviour that we are testing
        List<Authorities> authoritiesList = authoritiesRepository.findAll();

        //then - veryfy the output
        assertThat(authoritiesList).isNotNull();
        assertThat(authoritiesList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Junit test for save all authorities")
    public void givenAuthoritiesList_whenSaveAll_thenReturnAuthoritiesList() {

        //given - precondition or setup

        Authorities authorities1 = new Authorities();
        authorities1.setName("USER:UPDATE");

        List<Authorities> authoritiesList = List.of(authority,authorities1);


        //when - action or the behaviour that we are testing
        List<Authorities> foundAuthoritiesList = authoritiesRepository.saveAll(authoritiesList);

        //then - veryfy the output
        assertThat(foundAuthoritiesList).isNotNull();
        assertThat(foundAuthoritiesList.size()).isEqualTo(2);

    }

    @Test
    @DisplayName("Junit test for finding authority by id")
    public void givenAuthorityObject_whenFindById_thenReturnAuthorityObject() {

        //given - precondition or setup

        Authorities saveAuthority = authoritiesRepository.save(authority);


        //when - action or the behaviour that we are testing
        Authorities authoritiesInDb = authoritiesRepository.findById(saveAuthority.getId()).get();


        //then - veryfy the output
        assertThat(authoritiesInDb).isNotNull();

    }

    @Test
    @DisplayName("Junit test for finding authority by name")
    public void givenAuthorityName_whenFindByName_thenReturnAuthorityObject() {

        //given - precondition or setup
        authoritiesRepository.save(authority);

        //when - action or the behaviour that we are testing
        Authorities foundAuthorities = authoritiesRepository.findByName(authority.getName()).get();


        //then - veryfy the output
        assertThat(foundAuthorities).isNotNull();
    }

    @Test
    @DisplayName("Junit test for deleting authority with authority object")
    public void givenAuthorityObject_whenDelete_thenDeleteAuthority() {

        //given - precondition or setup
        authoritiesRepository.save(authority);


        //when - action or the behaviour that we are testing
        authoritiesRepository.delete(authority);
        Optional<Authorities> authoritiesOptional = authoritiesRepository.findById(authority.getId());


        //then - veryfy the output
        assertThat(authoritiesOptional).isEmpty();

    }

    @Test
    @DisplayName("Junit test for deleting authority with authority Id")
    public void givenAuthorityId_whenDelete_thenDeleteAuthority() {

        //given - precondition or setup
        Authorities authorities = new Authorities();
        authorities.setName("USER:READ");

        authoritiesRepository.save(authorities);


        //when - action or the behaviour that we are testing
        authoritiesRepository.deleteById(authorities.getId());
        Optional<Authorities> authoritiesOptional = authoritiesRepository.findById(authorities.getId());


        //then - veryfy the output
        assertThat(authoritiesOptional).isEmpty();

    }

}
