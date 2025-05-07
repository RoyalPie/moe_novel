package com.royal.iam_service.application.service;

import com.royal.iam_service.application.dto.request.SearchPermissionRequest;
import com.royal.iam_service.application.dto.response.PermissionDTO;

import java.util.List;

public interface PermissionQueryService {
    List<PermissionDTO> search(SearchPermissionRequest searchPermissionRequest);

    Long totalPermissions(SearchPermissionRequest searchPermissionRequest);
}
