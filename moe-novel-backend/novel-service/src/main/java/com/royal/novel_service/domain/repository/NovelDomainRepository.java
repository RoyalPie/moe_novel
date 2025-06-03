package com.royal.novel_service.domain.repository;

import com.evo.common.domainRepository.DomainRepository;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.query.NovelSearchQuery;

import java.util.List;
import java.util.UUID;

public interface NovelDomainRepository extends DomainRepository<Novel, UUID> {
    List<Novel> search(NovelSearchQuery searchNovelQuery);

    Novel getByNovelName(String novelName);

    int getChapterCount(String novelName);

    List<Novel> getAll();

    boolean existsByNovelNameAndAuthorName(String novelName, String authorName);

    Long count(NovelSearchQuery searchNovelQuery);

    List<String> getAllName();
}
