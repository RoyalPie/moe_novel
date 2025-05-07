package com.royal.iam_service.domain.repository;


import com.evo.common.domainRepository.DomainRepository;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.domain.query.SearchPermissionQuery;

import java.util.List;
import java.util.UUID;

public interface PermissionDomainRepository extends DomainRepository<Permission, UUID> {
    List<Permission> findPermissionByRoleId(UUID roleId);
    List<Permission> search(SearchPermissionQuery searchPermissionQuery);
    Long count(SearchPermissionQuery searchPermissionQuery);
}
