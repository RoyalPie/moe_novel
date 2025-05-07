package com.royal.iam_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.iam_service.domain.RolePermission;
import com.royal.iam_service.infrastructure.persistence.entity.RolePermissionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RolePermissionEntityMapper extends EntityMapper<RolePermission, RolePermissionEntity> {
}
