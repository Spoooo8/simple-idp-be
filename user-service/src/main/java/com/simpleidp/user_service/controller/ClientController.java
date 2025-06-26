package com.simpleidp.user_service.controller;

import com.simpleidp.user_service.dto.ClientDetailsDTO;
import com.simpleidp.user_service.dto.ClientRequest;
import com.simpleidp.user_service.dto.SimpleIDPClientDTO;
import com.simpleidp.user_service.entity.Client;
import com.simpleidp.user_service.entity.Scope;
import com.simpleidp.user_service.repository.ClientRepository;
import com.simpleidp.user_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final ClientRepository clientRepository;

    @PostMapping
    public ResponseEntity<ClientRequest> registerClient(
            @RequestBody ClientRequest request
    ) {
        return ResponseEntity.ok(clientService.registerClient(request));
    }

    @PostMapping("/simple-idp")
    public ResponseEntity<SimpleIDPClientDTO> createSimpleIDPClientUser(
            @RequestBody SimpleIDPClientDTO request) {
        return ResponseEntity.ok(clientService.createSimpleIDPClientUser(request));
    }

    @GetMapping
    public ResponseEntity<List<ClientDetailsDTO>> getClientDetails(){
        return ResponseEntity.ok(clientService.getClientDetails());
    }

    @GetMapping("/{clientId}/scopes")
    public ResponseEntity<Set<String>> getClientScopes(@PathVariable Long clientId) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Set<String> scopeNames = client.getScopes().stream()
                .map(Scope::getName)
                .collect(Collectors.toSet());

        return ResponseEntity.ok(scopeNames);
    }

}
