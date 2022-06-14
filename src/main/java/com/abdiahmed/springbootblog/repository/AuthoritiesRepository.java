package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthoritiesRepository extends JpaRepository<Authorities, Long> {
    Optional<Authorities> findByName(String name);
}
