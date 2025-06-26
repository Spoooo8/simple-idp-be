package com.simpleidp.identity_auth_server.repository;


import com.simpleidp.identity_auth_server.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}