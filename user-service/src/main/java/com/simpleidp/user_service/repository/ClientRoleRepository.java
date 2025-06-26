package com.simpleidp.user_service.repository;

import com.simpleidp.user_service.entity.ClientRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRoleRepository extends JpaRepository<ClientRole, Long> {
    List<ClientRole> findByClientId(Long clientId);
}
