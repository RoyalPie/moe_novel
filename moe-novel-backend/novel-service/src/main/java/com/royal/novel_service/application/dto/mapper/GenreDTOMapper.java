package com.royal.novel_service.application.dto.mapper;

import com.evo.common.mapper.DTOMapper;
import com.royal.novel_service.application.dto.response.GenreDTO;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.infrastructure.persistence.entity.GenreEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreDTOMapper extends DTOMapper<GenreDTO, Genre, GenreEntity> {
}
