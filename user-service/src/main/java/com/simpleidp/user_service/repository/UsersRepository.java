package com.simpleidp.user_service.repository;

import com.simpleidp.user_service.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Boolean existsByEmailAndClientId(String email, Long clientId);

    Set<Users> findByClientId(Long clientId);
}
