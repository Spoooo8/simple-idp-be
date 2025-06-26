package com.simpleidp.identity_auth_server.repository;

import com.simpleidp.identity_auth_server.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmailAndClientId(String email, Long clientId);

    Optional<Users> findByEmail(String username);
}