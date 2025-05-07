package com.royal.iam_service.infrastructure.persistence.repository;

import com.royal.iam_service.infrastructure.persistence.entity.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RolePermissionEntityRepository extends JpaRepository<RolePermissionEntity, UUID> {
    List<RolePermissionEntity> findByRoleIdInAndDeletedFalse(List<UUID> roleIds);
    List<RolePermissionEntity> findByRoleId(UUID id);
}
