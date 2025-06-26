package com.simpleidp.identity_auth_server.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.simpleidp.identity_auth_server.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Boolean existsByClientIdAndUserId(String clientId, Long userId);

    Optional<Client> findByClientId(String clientId);
}