package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.NovelChapter;
import com.royal.novel_service.infrastructure.persistence.entity.NovelChapterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelChapterEntityMapper extends EntityMapper<NovelChapter, NovelChapterEntity> {
}
