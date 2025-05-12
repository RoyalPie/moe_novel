package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.NovelReview;
import com.royal.novel_service.infrastructure.persistence.entity.NovelReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NovelReviewEntityMapper extends EntityMapper<NovelReview, NovelReviewEntity> {
}
