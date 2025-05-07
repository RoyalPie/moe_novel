package com.royal.iam_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.iam_service.domain.UserRole;
import com.royal.iam_service.infrastructure.persistence.entity.UserRoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserRoleEntityMapper extends EntityMapper<UserRole, UserRoleEntity> {
}
