package com.ideasync.ideasync.repository;

import com.ideasync.ideasync.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    long countByStatus(String status);

    List<User> findByStatus(String status);

    long countByRole(String role);
}
