package com.simpleidp.user_service.service;

import com.simpleidp.user_service.config.UserContext;
import com.simpleidp.user_service.dto.ClientDetailsDTO;
import com.simpleidp.user_service.dto.ClientRequest;
import com.simpleidp.user_service.dto.SimpleIDPClientDTO;
import com.simpleidp.user_service.dto.UsersRequestDTO;
import com.simpleidp.user_service.entity.Client;
import com.simpleidp.user_service.entity.GrantTypes;
import com.simpleidp.user_service.entity.Scope;
import com.simpleidp.user_service.entity.Users;
import com.simpleidp.user_service.repository.ClientRepository;
import com.simpleidp.user_service.repository.GrantTypesRepository;
import com.simpleidp.user_service.repository.ScopeRepository;
import com.simpleidp.user_service.repository.UsersRepository;
import com.simpleidp.user_service.utils.GrantType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final GrantTypesRepository grantTypesRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContext userContext;
    private final ScopeRepository scopeRepository;

    public ClientRequest registerClient(ClientRequest request) {

        Boolean clientIdExists = clientRepository.existsByClientIdAndUserId(request.getClientId(), request.getUserId());
        if (clientIdExists) {
            throw new IllegalArgumentException("client id already exists");
        }

        Set<GrantTypes> grantTypes = new HashSet<>(grantTypesRepository.findAllById(request.getGrantTypesIds()));
        if (grantTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one valid grant type must be provided");
        }

        Set<Scope> scopes = new HashSet<>(scopeRepository.findAllById(request.getScopeIds()));
        if (scopes.isEmpty()) {
            throw new IllegalArgumentException("At least one valid scope must be provided");
        }

        Users user = usersRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user not found"));

        Client client = new Client();
        client.setClientId(request.getClientId());
        client.setName(request.getName());
        client.setGrantTypes(grantTypes);
        client.setScopes(scopes);
        client.setRedirectUrl(request.getRedirectUrl());
        client.setUser(user);
        client.setClientSecret(UUID.randomUUID().toString());

        clientRepository.save(client);
        return request;
    }


    public SimpleIDPClientDTO createSimpleIDPClientUser(SimpleIDPClientDTO request) {

        Set<GrantTypes> grantTypes = new HashSet<>(grantTypesRepository.findAllById(request.getGrantTypesIds()));
        if (grantTypes.isEmpty()) {
            throw new IllegalArgumentException("At least one valid grant type must be provided");
        }

        Users idpUser = new Users();

        idpUser.setName(request.getName());
        idpUser.setEmail(request.getEmail());
        idpUser.setPassword(passwordEncoder.encode(request.getPassword()));
        usersRepository.save(idpUser);

        Client client = new Client();
        client.setClientId(request.getClientId());
        client.setName(request.getName());
        client.setGrantTypes(grantTypes);
        client.setUser(idpUser);
        client.setClientSecret(UUID.randomUUID().toString());
        clientRepository.save(client);

        idpUser.setClient(client);
        usersRepository.save(idpUser);

        return request;
    }

    public List<ClientDetailsDTO> getClientDetails() {
        Integer userId = Integer.valueOf(userContext.getUserId());
        Set<Client> clients = clientRepository.findByUserId(userId);

        List<ClientDetailsDTO> clientDetailsDTOs = clients.stream()
                .map(client -> {
                    ClientDetailsDTO dto = new ClientDetailsDTO();
                    dto.setId(client.getId());
                    dto.setClientId(client.getClientId());
                    dto.setName(client.getName());
                   dto.setClientSecret(client.getClientSecret());

                    Set<GrantType> grantTypes = client.getGrantTypes().stream()
                            .map(GrantTypes::getGrantType)
                            .collect(Collectors.toSet());
                    String grantTypeString = "";

                    if (grantTypes.size() == 2 &&
                            grantTypes.contains(GrantType.AUTHORIZATION_CODE) &&
                            grantTypes.contains(GrantType.REFRESH_TOKEN)) {
                        grantTypeString = "Authorization Code with PKCE";
                    } else if (grantTypes.size() == 1) {
                        GrantType type = grantTypes.iterator().next();
                        switch (type) {
                            case CLIENT_CREDENTIALS: grantTypeString = "Client Credentials"; break;
                            case REFRESH_TOKEN: grantTypeString = "Refresh Token"; break;
                            case AUTHORIZATION_CODE: grantTypeString = "Authorization Code"; break;
                        }
                    }

                    dto.setGrantTypes(grantTypeString);
                    dto.setRedirectUrl(client.getRedirectUrl());
                    dto.setScopes(
                            client.getScopes()
                                    .stream()
                                    .map(Scope::getName)
                                    .collect(Collectors.joining(" "))
                    );

                    return dto;
                })
                .collect(Collectors.toList());

        return clientDetailsDTOs;
    }
}
