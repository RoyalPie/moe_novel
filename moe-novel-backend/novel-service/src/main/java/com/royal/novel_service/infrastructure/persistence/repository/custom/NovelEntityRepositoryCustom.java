package com.royal.novel_service.infrastructure.persistence.repository.custom;

import com.royal.novel_service.domain.query.SearchNovelQuery;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;

import java.util.List;

public interface NovelEntityRepositoryCustom {
    List<NovelEntity> search(SearchNovelQuery searchNovelQuery);
    Long count(SearchNovelQuery searchNovelQuery);
}