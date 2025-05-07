package com.royal.iam_service.domain;

import com.royal.iam_service.domain.command.CreateOrUpdatePermissionCmd;
import com.evo.common.webapp.support.IdUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Permission {
    private UUID permissionId;
    private String scope;
    private String resource;
    private boolean deleted;

    public Permission(CreateOrUpdatePermissionCmd cmd) {
        this.permissionId = IdUtils.newUUID();
        this.resource = cmd.getResource();
        this.scope = cmd.getScope();
        this.deleted = false;
    }

    public Permission update(CreateOrUpdatePermissionCmd cmd) {
        this.resource = cmd.getResource();
        this.scope = cmd.getScope();
        return this;
    }
}
