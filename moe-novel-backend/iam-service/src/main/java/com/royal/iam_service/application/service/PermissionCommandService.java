package com.royal.iam_service.application.service;

import com.royal.iam_service.application.dto.request.CreateOrUpdatePermissionRequest;
import com.royal.iam_service.application.dto.response.PermissionDTO;

public interface PermissionCommandService {
    PermissionDTO createPermission(CreateOrUpdatePermissionRequest request);

    PermissionDTO updatePermission(CreateOrUpdatePermissionRequest request);

}
