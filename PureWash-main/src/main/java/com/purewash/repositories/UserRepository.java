package com.purewash.repositories;

import com.purewash.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);

    Optional<User> findByEmail(String email);
}
