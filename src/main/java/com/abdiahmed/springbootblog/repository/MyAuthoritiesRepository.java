package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.MyAuthorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MyAuthoritiesRepository extends JpaRepository<MyAuthorities, Long> {
    Optional<MyAuthorities> findByName(String name);
}
