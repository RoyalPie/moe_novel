package com.royal.iam_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.iam_service.domain.UserActivityLog;
import com.royal.iam_service.infrastructure.persistence.entity.UserActivityLogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserActivityLogEntityMapper extends EntityMapper<UserActivityLog, UserActivityLogEntity> {
}
