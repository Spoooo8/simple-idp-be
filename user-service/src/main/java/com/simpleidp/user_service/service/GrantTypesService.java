package com.simpleidp.user_service.service;

import com.simpleidp.user_service.dto.GrantTypeDropDTO;
import com.simpleidp.user_service.entity.GrantTypes;
import com.simpleidp.user_service.repository.GrantTypesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service@RequiredArgsConstructor
public class GrantTypesService {

    private final GrantTypesRepository grantTypesRepository;


    public List<GrantTypeDropDTO> getGrantTypesForDropdown() {
        List<GrantTypes> allGrantTypes = grantTypesRepository.findAll();

        // Build a map of grantType name -> ID
        Map<String, Long> grantTypeMap = allGrantTypes.stream()
                .collect(Collectors.toMap(g -> g.getGrantType().name(), GrantTypes::getId));

        List<GrantTypeDropDTO> dropdown = new ArrayList<>();

        // Add Client Credentials option
        if (grantTypeMap.containsKey("CLIENT_CREDENTIALS")) {
            dropdown.add(new GrantTypeDropDTO("Client Credentials",
                    Collections.singleton(grantTypeMap.get("CLIENT_CREDENTIALS"))));
        }

        // Add Authorization Code option
        if (grantTypeMap.containsKey("AUTHORIZATION_CODE")) {
            dropdown.add(new GrantTypeDropDTO("Authorization Code",
                    Collections.singleton(grantTypeMap.get("AUTHORIZATION_CODE"))));
        }

        // Add PKCE option (combination of AUTHORIZATION_CODE and REFRESH_TOKEN)
        if (grantTypeMap.containsKey("AUTHORIZATION_CODE") && grantTypeMap.containsKey("REFRESH_TOKEN")) {
            Set<Long> pkceIds = new HashSet<>();
            pkceIds.add(grantTypeMap.get("AUTHORIZATION_CODE"));
            pkceIds.add(grantTypeMap.get("REFRESH_TOKEN"));

            dropdown.add(new GrantTypeDropDTO("Authorization Code with PKCE", pkceIds));
        }

        return dropdown;
    }
}
