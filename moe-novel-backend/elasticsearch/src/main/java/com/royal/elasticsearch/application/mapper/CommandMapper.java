package com.royal.elasticsearch.application.mapper;

import com.evo.common.dto.request.SyncNovelRequest;
import com.evo.common.dto.request.SyncUserRequest;
import com.royal.elasticsearch.domain.command.SyncNovelCmd;
import com.royal.elasticsearch.domain.command.SyncUserCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    SyncUserCmd from(SyncUserRequest syncUserRequest);
    SyncNovelCmd from(SyncNovelRequest syncNovelRequest);

}
