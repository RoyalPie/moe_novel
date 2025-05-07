package com.royal.iam_service.application.dto.request.identityKeycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogoutRequest {
    private String client_id;
    private String client_secret;
    private String refresh_token;
}
