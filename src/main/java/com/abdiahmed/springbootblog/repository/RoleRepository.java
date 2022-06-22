package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);
}
