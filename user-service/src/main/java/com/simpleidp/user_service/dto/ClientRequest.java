package com.simpleidp.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {
    private String clientId;
    private String name;
    private Long userId;
    private Set<Long> grantTypesIds;
    private Set<Long> scopeIds;
    private String redirectUrl;
}
