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
public class SimpleIDPClientDTO {
    private String email;
    private String name;
    private String password;
    private String clientId;
    private Set<Long> grantTypesIds;
}
