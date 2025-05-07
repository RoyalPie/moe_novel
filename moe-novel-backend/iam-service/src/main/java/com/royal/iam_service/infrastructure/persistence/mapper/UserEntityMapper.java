package com.royal.iam_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.iam_service.domain.User;
import com.royal.iam_service.infrastructure.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper extends EntityMapper<User, UserEntity> {

}
