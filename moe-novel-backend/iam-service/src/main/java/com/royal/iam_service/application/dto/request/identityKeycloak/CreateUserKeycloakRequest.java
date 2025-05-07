package com.royal.iam_service.application.dto.request.identityKeycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CreateUserKeycloakRequest {
    private String username;
    private boolean enabled;
    private String email;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private List<CredentialRequest> credentials;
}
