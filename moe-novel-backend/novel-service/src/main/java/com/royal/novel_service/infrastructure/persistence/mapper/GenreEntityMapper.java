package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.infrastructure.persistence.entity.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreEntityMapper extends EntityMapper<Genre, GenreEntity> {
}
