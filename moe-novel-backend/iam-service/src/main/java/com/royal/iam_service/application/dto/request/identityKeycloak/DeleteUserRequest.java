package com.royal.iam_service.application.dto.request.identityKeycloak;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeleteUserRequest {
    private String deletedBody;
}
