package com.royal.iam_service.application.service;

import com.royal.iam_service.application.dto.request.CreateOrUpdateRoleRequest;
import com.royal.iam_service.application.dto.response.RoleDTO;

public interface RoleCommandService {
    RoleDTO createRole(CreateOrUpdateRoleRequest createOrUpdateRoleRequest);

    RoleDTO updateRole(CreateOrUpdateRoleRequest createOrUpdateRoleRequest);
}
