package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelEntityMapper extends EntityMapper<Novel, NovelEntity> {
}
