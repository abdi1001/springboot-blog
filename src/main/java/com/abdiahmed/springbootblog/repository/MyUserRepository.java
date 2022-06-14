package com.abdiahmed.springbootblog.repository;

import com.abdiahmed.springbootblog.model.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface
MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String user);
}
