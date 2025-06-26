package com.simpleidp.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersRequestDTO {
    private String email;
    private String name;
    private String password;
    private Long clientId;
    private Long clientRoleId;
}
