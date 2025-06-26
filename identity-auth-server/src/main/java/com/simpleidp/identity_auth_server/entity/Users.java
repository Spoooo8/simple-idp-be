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
@Table(schema = "auth", name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    // The client this user belongs to
    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonBackReference("client-with-users")
    private Client client;

    // Authorities/roles assigned to this user
    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    // Clients owned by this user
    @OneToMany(mappedBy = "user")
    @JsonManagedReference("users-owning-client")
    private Set<Client> ownedClients;
}