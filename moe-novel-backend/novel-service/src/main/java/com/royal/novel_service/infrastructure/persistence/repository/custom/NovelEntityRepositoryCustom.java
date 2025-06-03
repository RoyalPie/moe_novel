package com.royal.novel_service.infrastructure.persistence.repository.custom;

import com.royal.novel_service.domain.query.NovelSearchQuery;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;

import java.util.List;

public interface NovelEntityRepositoryCustom {
    List<NovelEntity> search(NovelSearchQuery searchNovelQuery);

    Long count(NovelSearchQuery searchNovelQuery);
}