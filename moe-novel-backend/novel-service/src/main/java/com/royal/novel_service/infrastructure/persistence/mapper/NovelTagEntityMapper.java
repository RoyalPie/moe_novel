package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.NovelTag;
import com.royal.novel_service.infrastructure.persistence.entity.NovelTagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelTagEntityMapper extends EntityMapper<NovelTag, NovelTagEntity> {
}
