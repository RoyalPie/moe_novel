package com.royal.iam_service.domain;

import com.royal.iam_service.domain.command.CreateRolePermissionCmd;
import com.evo.common.webapp.support.IdUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class RolePermission {
    private UUID id;
    private UUID roleId;
    private UUID permissionId;
    private boolean deleted;

    public RolePermission(CreateRolePermissionCmd cmd) {
        this.id = IdUtils.newUUID();
        this.roleId = cmd.getRoleId();
        this.permissionId = cmd.getPermissionId();
        this.deleted = false;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RolePermission that = (RolePermission) o;
        return Objects.equals(roleId, that.roleId) && Objects.equals(permissionId, that.permissionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), roleId, permissionId);
    }

}
