package com.simpleidp.user_service.repository;

import com.simpleidp.user_service.entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScopeRepository extends JpaRepository<Scope, Long> {
}
