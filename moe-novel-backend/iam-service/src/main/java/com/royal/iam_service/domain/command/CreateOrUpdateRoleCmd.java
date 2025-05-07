package com.royal.iam_service.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateOrUpdateRoleCmd {
    private UUID id;
    private String name;
    private boolean root;
    private boolean deleted;
    private List<CreateRolePermissionCmd> rolePermission;
}
