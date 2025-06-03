package com.royal.novel_service.infrastructure.persistence.mapper;

import com.evo.common.mapper.EntityMapper;
import com.royal.novel_service.domain.Review;
import com.royal.novel_service.infrastructure.persistence.entity.ReviewEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReviewEntityMapper extends EntityMapper<Review, ReviewEntity> {
}
