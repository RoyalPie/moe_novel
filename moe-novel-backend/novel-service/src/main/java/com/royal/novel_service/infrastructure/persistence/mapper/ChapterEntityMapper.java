package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.infrastructure.persistence.entity.ChapterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterEntityMapper extends EntityMapper<Chapter, ChapterEntity> {
}
