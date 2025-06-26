package com.simpleidp.user_service.controller;

import com.simpleidp.user_service.dto.ScopeRequest;
import com.simpleidp.user_service.entity.Scope;
import com.simpleidp.user_service.service.ScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/scopes")
public class ScopeController {

    @Autowired
    private ScopeService scopeService;

    // 🔥 POST API to create a new scope
    @PostMapping("/create")
    public ResponseEntity<Scope> createScope(@RequestBody ScopeRequest request) {
        Scope savedScope = scopeService.createScope(request);
        return ResponseEntity.ok(savedScope);
    }

    @GetMapping
    public ResponseEntity<List<Scope>> getAllScopes() {
        List<Scope> scopes = scopeService.getAllScopes();
        return ResponseEntity.ok(scopes);
    }
}