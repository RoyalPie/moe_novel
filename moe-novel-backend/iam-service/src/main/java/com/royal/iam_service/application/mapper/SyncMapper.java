package com.royal.iam_service.application.mapper;

import com.evo.common.dto.request.SyncUserRequest;
import com.royal.iam_service.domain.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    SyncUserRequest from(User user);
}