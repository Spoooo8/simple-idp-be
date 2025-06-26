package com.simpleidp.user_service.service;

import com.simpleidp.user_service.dto.ScopeRequest;
import com.simpleidp.user_service.entity.Scope;
import com.simpleidp.user_service.repository.ScopeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScopeService {

    @Autowired
    private ScopeRepository scopeRepository;

    public Scope createScope(ScopeRequest request) {
        Scope scope = new Scope();
        scope.setName(request.getName());

        return scopeRepository.save(scope);
    }

    public List<Scope> getAllScopes() {
        return scopeRepository.findAll();
    }

}
