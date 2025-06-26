package com.simpleidp.user_service.service;

import com.simpleidp.user_service.dto.ClientRoleResponseDTO;
import com.simpleidp.user_service.entity.Client;
import com.simpleidp.user_service.entity.ClientRole;
import com.simpleidp.user_service.repository.ClientRepository;
import com.simpleidp.user_service.repository.ClientRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientRoleService {

    private final ClientRoleRepository clientRoleRepository;
    private final ClientRepository clientRepository;

    public void createRole(ClientRoleResponseDTO request) {
        Client client = clientRepository.findById(request.getClientRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        ClientRole clientRole = new ClientRole();
        clientRole.setName(request.getRoleName());
        clientRole.setClient(client);

        clientRoleRepository.save(clientRole);
    }

    public List<ClientRoleResponseDTO> getRolesByClientId(Long clientId) {
        List<ClientRole> roles = clientRoleRepository.findByClientId(clientId);
        return roles.stream()
                .map(role -> new ClientRoleResponseDTO(role.getId(), role.getName()))
                .toList();
    }
}
