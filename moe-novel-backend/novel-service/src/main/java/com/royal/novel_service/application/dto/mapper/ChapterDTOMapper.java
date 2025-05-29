package com.royal.novel_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.novel_service.application.dto.response.ChapterDTO;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.infrastructure.persistence.entity.ChapterEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChapterDTOMapper extends DTOMapper<ChapterDTO, Chapter, ChapterEntity> {
}
