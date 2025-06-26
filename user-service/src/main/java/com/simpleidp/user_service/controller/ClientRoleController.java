package com.simpleidp.user_service.controller;

import com.simpleidp.user_service.dto.ClientRoleResponseDTO;
import com.simpleidp.user_service.service.ClientRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client-roles")
@RequiredArgsConstructor
public class ClientRoleController {

    private final ClientRoleService clientRoleService;

    @PostMapping
    public ResponseEntity<String> createRole(@RequestBody ClientRoleResponseDTO request) {
        clientRoleService.createRole(request);
        return ResponseEntity.ok("Role created successfully");
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<List<ClientRoleResponseDTO>> getRolesByClientId(@PathVariable Long clientId) {
        List<ClientRoleResponseDTO> roles = clientRoleService.getRolesByClientId(clientId);
        return ResponseEntity.ok(roles);
    }
}
