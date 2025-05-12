package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.infrastructure.persistence.entity.NovelGenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelGenreEntityMapper extends EntityMapper<NovelGenre, NovelGenreEntity> {
}
