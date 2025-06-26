package com.simpleidp.user_service.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.simpleidp.user_service.entity.GrantTypes;
import com.simpleidp.user_service.entity.Users;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClientDetailsDTO {
    private Long id;
    private String clientId;
    private String name;
    private String clientSecret;
    private String grantTypes;
    private String redirectUrl;
    private String scopes;
}
