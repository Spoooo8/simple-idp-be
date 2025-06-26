package com.simpleidp.identity_auth_server.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Entity
@Table(schema = "auth", name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String clientId;

    private String clientSecret;

    // The user who owns this client
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference("users-owning-client")
    private Users user;

    // Grant types associated with this client
    @ManyToMany
    @JoinTable(
            name = "client_grant_type",
            schema = "auth",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "grant_type_id")
    )
    private Set<GrantTypes> grantTypes;

    // Users associated with this client
    @OneToMany(mappedBy = "client")
    @JsonManagedReference("client-with-users")
    private Set<Users> users;

    private String redirectUrl;

    @ManyToMany
    @JoinTable(
            name = "client_scope",
            schema = "auth",
            joinColumns = @JoinColumn(name = "client_id"),
            inverseJoinColumns = @JoinColumn(name = "scope_id")
    )
    private Set<Scope> scopes;


}