package com.royal.novel_service.application.mapper;

import com.royal.novel_service.application.dto.request.novel.NovelSearchRequest;
import com.royal.novel_service.domain.query.NovelSearchQuery;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QueryMapper {
    NovelSearchQuery from(NovelSearchRequest request);
}
