package com.royal.iam_service.application.service;

import com.evo.common.dto.TokenDTO;
import com.royal.iam_service.application.dto.request.LoginRequest;
import com.royal.iam_service.domain.command.ResetKeycloakPasswordCmd;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthServiceCommand {
    TokenDTO authenticate(LoginRequest loginRequest);

    void logoutIam(HttpServletRequest request, String refreshToken);

    TokenDTO refresh(String refreshToken);

    void requestPasswordReset(String username, ResetKeycloakPasswordCmd resetKeycloakPasswordCmd);

    void resetPassword(String token, ResetKeycloakPasswordCmd resetKeycloakPasswordCmd);
}
