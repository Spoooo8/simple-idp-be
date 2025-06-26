package com.simpleidp.user_service.repository;

import com.simpleidp.user_service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByClientIdAndUserId(String clientId, Long userId);

    Set<Client> findByUserId(Integer userId);
}
