package com.royal.novel_service.application.mapper;

import com.royal.novel_service.application.dto.request.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.UpdateNovelRequest;
import com.royal.novel_service.domain.command.CreateNovelCmd;
import com.royal.novel_service.domain.command.UpdateNovelCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateNovelCmd from(CreateNovelRequest request);
    UpdateNovelCmd from(UpdateNovelRequest request);
}
