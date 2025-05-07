package com.royal.iam_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.iam_service.domain.Role;
import com.royal.iam_service.infrastructure.persistence.entity.RoleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper extends EntityMapper<Role, RoleEntity> {

}
