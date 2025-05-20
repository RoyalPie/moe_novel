package com.royal.novel_service.application.dto.mapper;

import com.evo.common.dto.request.SyncNovelRequest;
import com.royal.novel_service.domain.Novel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SyncMapper {
    @Mapping(source = "novelGenres", target = "novelGenres")
    @Mapping(source = "novelTags", target = "novelTags")
    SyncNovelRequest from(Novel novel,
                          List<String> novelGenres,
                          List<String> novelTags
    );

}
