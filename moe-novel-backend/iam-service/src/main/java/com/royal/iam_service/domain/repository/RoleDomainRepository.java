package com.royal.iam_service.domain.repository;

import com.evo.common.domainRepository.DomainRepository;
import com.royal.iam_service.domain.Permission;
import com.royal.iam_service.domain.Role;

import java.util.List;
import java.util.UUID;

public interface RoleDomainRepository extends DomainRepository<Role, UUID> {
    Role findByName(String name);
    List<Permission> findPermissionByRoleId(UUID roleId);
}
