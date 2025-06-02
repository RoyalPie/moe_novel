package com.royal.novel_service.domain.repository;

import com.evo.common.domainRepository.DomainRepository;
import com.royal.novel_service.domain.Tag;
import com.royal.novel_service.domain.query.SearchTagQuery;

import java.util.List;
import java.util.UUID;

public interface TagDomainRepository extends DomainRepository<Tag, UUID> {
    Tag findByName(String name);

    List<Tag> search(SearchTagQuery searchTagQuery);

    Long count(SearchTagQuery searchTagQuery);

    Boolean exitsByName(String name);
}
