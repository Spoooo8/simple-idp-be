package com.simpleidp.identity_auth_server.config;

import com.simpleidp.identity_auth_server.entity.Client;
import com.simpleidp.identity_auth_server.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;

import java.time.Duration;

public class JpaRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public JpaRegisteredClientRepository(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        throw new UnsupportedOperationException("Client registration via code not supported");
    }

    @Override
    public RegisteredClient findById(String id) {
        return clientRepository.findById(Long.valueOf(id))
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        if ("unilinkauth".equals(clientId)) {
            return getStaticPKCEClient();
        }

        return clientRepository.findByClientId(clientId)
                .map(this::toRegisteredClient)
                .orElse(null);
    }

    private RegisteredClient getStaticPKCEClient() {
        return RegisteredClient.withId("unilinkauth-id")
                .clientId("unilinkauth")
                .clientAuthenticationMethod(ClientAuthenticationMethod.NONE)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .redirectUri("https://simpleidp.netlify.app/callback")
                .scope("openid")
                .scope("email")
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .requireProofKey(true)
                        .build())
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(15))
                        .refreshTokenTimeToLive(Duration.ofHours(8))
                        .reuseRefreshTokens(false)
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .build())
                .build();
    }

    private RegisteredClient toRegisteredClient(Client client) {
        RegisteredClient.Builder builder = RegisteredClient.withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientAuthenticationMethod(
                        client.getClientSecret() == null
                                ? ClientAuthenticationMethod.NONE
                                : ClientAuthenticationMethod.CLIENT_SECRET_BASIC
                )
                .tokenSettings(TokenSettings.builder()
                        .accessTokenTimeToLive(Duration.ofMinutes(10))
                        .refreshTokenTimeToLive(Duration.ofHours(8))
                        .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                        .reuseRefreshTokens(false)
                        .build())
                .clientSettings(ClientSettings.builder()
                        .requireAuthorizationConsent(false)
                        .requireProofKey(true)
                        .build());

        // ✅ Set redirect URI
        builder.redirectUri(client.getRedirectUrl());

        // ✅ Set grant types
        client.getGrantTypes().forEach(grant ->
                builder.authorizationGrantType(new AuthorizationGrantType(grant.getGrantType().name()))
        );

        // ✅ Set scopes
        if (client.getScopes() != null && !client.getScopes().isEmpty()) {
            client.getScopes().forEach(scope ->
                    builder.scope(scope.getName())
            );
        } else {
            builder.scope("openid");
            builder.scope("email");
        }

        // ✅ Encode client secret if present
        if (client.getClientSecret() != null) {
            builder.clientSecret(passwordEncoder.encode(client.getClientSecret()));
        }

        return builder.build();
    }

}
