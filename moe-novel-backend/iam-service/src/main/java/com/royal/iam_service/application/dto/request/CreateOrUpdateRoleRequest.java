package com.royal.iam_service.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrUpdateRoleRequest {
    private UUID id;
    private String name;
    private boolean isRoot;
    private List<CreateRolePermissionRequest> rolePermission;
}


