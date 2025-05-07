package com.royal.iam_service.domain.command;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class DeleteRolePermissionCmd {
    private UUID roleId;
    private UUID permissionId;
}
