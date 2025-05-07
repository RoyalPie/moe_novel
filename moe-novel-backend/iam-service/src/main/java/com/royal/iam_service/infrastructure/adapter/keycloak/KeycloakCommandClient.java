package com.royal.iam_service.infrastructure.adapter.keycloak;

import com.royal.iam_service.application.dto.request.CreateUserRequest;
import com.royal.iam_service.domain.command.ResetKeycloakPasswordCmd;

import java.util.UUID;

public interface KeycloakCommandClient {
    String createKeycloakUser(CreateUserRequest request);
    void resetPassword(String token, UUID keycloakUserId, ResetKeycloakPasswordCmd resetKeycloakPasswordCmd);
}
