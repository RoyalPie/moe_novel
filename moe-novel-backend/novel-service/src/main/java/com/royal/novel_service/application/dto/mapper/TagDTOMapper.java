package com.royal.novel_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.novel_service.application.dto.response.TagDTO;
import com.royal.novel_service.domain.Tag;
import com.royal.novel_service.infrastructure.persistence.entity.TagEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagDTOMapper extends DTOMapper<TagDTO, Tag, TagEntity> {
}
