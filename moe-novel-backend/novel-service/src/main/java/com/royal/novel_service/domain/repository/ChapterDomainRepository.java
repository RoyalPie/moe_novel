package com.royal.novel_service.domain.repository;

import com.evo.common.domainRepository.DomainRepository;
import com.royal.novel_service.domain.Chapter;

import java.util.List;
import java.util.UUID;

public interface ChapterDomainRepository extends DomainRepository<Chapter, UUID> {
    List<Chapter> findByChapterNumberAndTilte(String novelTitle, int chapterNumber);

}
