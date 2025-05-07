package com.royal.iam_service.infrastructure.persistence.repository.custom;

import com.royal.iam_service.domain.query.SearchPermissionQuery;
import com.royal.iam_service.infrastructure.persistence.entity.PermissionEntity;

import java.util.List;

public interface PermissionEntityRepositoryCustom {
    List<PermissionEntity> search(SearchPermissionQuery searchPermissionQuery);
    Long count(SearchPermissionQuery searchPermissionQuery);
}
