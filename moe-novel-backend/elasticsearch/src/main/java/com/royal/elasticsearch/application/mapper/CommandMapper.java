package com.royal.elasticsearch.application.mapper;

import com.evo.common.dto.request.SyncUserRequest;
import com.royal.elasticsearch.domain.command.SyncUserCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncUserCmd from(SyncUserRequest syncUserRequest);
}
