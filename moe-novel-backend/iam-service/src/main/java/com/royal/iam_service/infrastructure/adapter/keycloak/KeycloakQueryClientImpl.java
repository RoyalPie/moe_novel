package com.royal.iam_service.infrastructure.adapter.keycloak;

import com.evo.common.dto.TokenDTO;
import com.royal.iam_service.application.dto.request.identityKeycloak.GetTokenRequest;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KeycloakQueryClientImpl implements KeycloakQueryClient {
    private final KeycloakIdentityClient keycloakIdentityClient;

    @Value("${keycloak.iam.client-id}")
    private String clientId;
    @Value("${keycloak.iam.client-secret}")
    private String clientSecret;

    @Override
    public String getClientToken() {
        try {
            TokenDTO tokenDTO = keycloakIdentityClient.getToken(GetTokenRequest.builder()
                    .grant_type("client_credentials")
                    .client_id(clientId)
                    .client_secret(clientSecret)
                    .scope("openid")
                    .build());
            return tokenDTO.getAccessToken();
        } catch (FeignException e) {
            throw new RuntimeException("Cant get token");
        }
    }
}
