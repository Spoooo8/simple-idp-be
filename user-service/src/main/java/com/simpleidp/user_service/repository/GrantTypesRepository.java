package com.simpleidp.user_service.repository;

import com.simpleidp.user_service.entity.GrantTypes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GrantTypesRepository extends JpaRepository<GrantTypes, Long> {
}
