package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.MyRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyRolesRepository extends JpaRepository<MyRoles, Long> {

    Optional<MyRoles> findByName(String name);
}
