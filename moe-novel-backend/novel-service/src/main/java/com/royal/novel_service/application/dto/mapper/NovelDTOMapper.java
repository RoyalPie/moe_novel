package com.royal.novel_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelDTOMapper extends DTOMapper<NovelDTO, Novel, NovelEntity> {
}