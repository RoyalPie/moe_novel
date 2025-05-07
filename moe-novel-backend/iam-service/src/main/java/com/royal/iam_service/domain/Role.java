package com.royal.iam_service.domain;

import com.royal.iam_service.domain.command.CreateOrUpdateRoleCmd;
import com.royal.iam_service.domain.command.CreateRolePermissionCmd;
import com.evo.common.webapp.support.IdUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Role {
    private UUID roleId;
    private String name;
    private boolean root;
    private boolean deleted;
    private List<RolePermission> rolePermissions;

    public Role(CreateOrUpdateRoleCmd cmd) {
        if (cmd.getId() != null) {
            this.roleId = cmd.getId();
        } else {
            this.roleId = IdUtils.newUUID();
        }
        this.name = cmd.getName();
        this.deleted= cmd.isDeleted();
        this.root = cmd.isRoot();

        this.rolePermissions = new ArrayList<>();
        if (cmd.getRolePermission() != null) {
            cmd.getRolePermission().forEach(createRolePermissionCmd -> {
                createRolePermissionCmd.setRoleId(this.roleId);
                rolePermissions.add(new RolePermission(createRolePermissionCmd));
            });
        }
    }

    public void update(CreateOrUpdateRoleCmd cmd) {
        this.name = cmd.getName();
        this.deleted = cmd.isDeleted();
        this.root = cmd.isRoot();
        if (this.rolePermissions == null) {
            this.rolePermissions = new ArrayList<>();
        }

        // Tạo map chứa rolePermission hiện tại và tạm thời xoá mềm
        Map<UUID, RolePermission> existingPermissionsMap = this.rolePermissions.stream()
                .peek(rp -> rp.setDeleted(true))
                .collect(Collectors.toMap(RolePermission::getPermissionId, rp -> rp));

        for (CreateRolePermissionCmd rolePermissionCmd : cmd.getRolePermission()) {
            UUID permissionId = rolePermissionCmd.getPermissionId();
            if (existingPermissionsMap.containsKey(permissionId)) {
                // Nếu đã tồn tại, cập nhật deleted = false
                existingPermissionsMap.get(permissionId).setDeleted(false);
            } else {
                // Nếu chưa tồn tại, tạo mới RolePermission
                rolePermissionCmd.setRoleId(this.roleId);
                RolePermission newRolePermission = new RolePermission(rolePermissionCmd);
                this.rolePermissions.add(newRolePermission);
            }
        }
    }

}
