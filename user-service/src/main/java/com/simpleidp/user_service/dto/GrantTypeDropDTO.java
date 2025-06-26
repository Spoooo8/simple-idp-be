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
public class GrantTypeDropDTO {
    private String label;             // Display name (like 'Authorization Code', 'Client Credentials', 'PKCE')
    private Set<Long> value;  // Set of grant type IDs for that flow
}

