package com.simpleidp.identity_auth_server.repository;

import com.simpleidp.identity_auth_server.entity.GrantTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantTypesRepository extends JpaRepository<GrantTypes, Long> {
}