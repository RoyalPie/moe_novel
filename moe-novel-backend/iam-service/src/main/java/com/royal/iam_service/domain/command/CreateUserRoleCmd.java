package com.royal.iam_service.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateUserRoleCmd {
    private UUID roleId;
    private UUID userId;
}
