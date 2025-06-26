package com.simpleidp.user_service.service;

import com.simpleidp.user_service.config.UserContext;
import com.simpleidp.user_service.dto.ClientDetailsDTO;
import com.simpleidp.user_service.dto.UserDetailsDTO;
import com.simpleidp.user_service.dto.UserInfoDTO;
import com.simpleidp.user_service.dto.UsersRequestDTO;
import com.simpleidp.user_service.entity.*;
import com.simpleidp.user_service.repository.AuthorityRepository;
import com.simpleidp.user_service.repository.ClientRepository;
import com.simpleidp.user_service.repository.ClientRoleRepository;
import com.simpleidp.user_service.repository.UsersRepository;
import com.simpleidp.user_service.utils.GrantType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsersService {

    private final UsersRepository usersRepository;
    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContext userContext;
    private final ClientRoleRepository clientRoleRepository;
    private final AuthorityRepository authorityRepository;


    public UsersRequestDTO createUser(UsersRequestDTO request) {

        Boolean userExists = usersRepository.existsByEmailAndClientId(request.getEmail(), request.getClientId());
        if (userExists) {
            throw new IllegalArgumentException("Email already exists");
        }

        Client client = clientRepository.findById(request.getClientId())
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));

        Users newUser = new Users();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setClient(client);

        usersRepository.save(newUser);

        // 👉 Create Authority for User only if clientRoleId is provided
        if (request.getClientRoleId() != null) {
            ClientRole clientRole = clientRoleRepository.findById(request.getClientRoleId())
                    .orElseThrow(() -> new IllegalArgumentException("Client role not found"));

            Authority authority = new Authority();
            authority.setUsers(newUser);
            authority.setClientRole(clientRole);

            authorityRepository.save(authority);
        }

        return request;
    }


    public UserInfoDTO getUser() {
        String userId = userContext.getUserId();
        Users users = usersRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setEmail(users.getEmail());
        userInfoDTO.setName(users.getName());
        userInfoDTO.setUserId(users.getId());
        return userInfoDTO;
    }


    public List<UserDetailsDTO> getUserDetails(Long clientId) {
        Set<Users> users = usersRepository.findByClientId(clientId);

        List<UserDetailsDTO> clientDetailsDTOs = users.stream()
                .map(user -> {
                    UserDetailsDTO dto = new UserDetailsDTO();
                    dto.setId(user.getId());
                    dto.setClientId(clientId);
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    Set<String> roleNames = user.getAuthorities()
                            .stream()
                            .map(Authority::getClientRole)
                            .map(ClientRole::getName)
                            .collect(Collectors.toSet());
                    dto.setRoles(roleNames);

                    return dto;
                })
                .collect(Collectors.toList());

        return clientDetailsDTOs;
    }
}
