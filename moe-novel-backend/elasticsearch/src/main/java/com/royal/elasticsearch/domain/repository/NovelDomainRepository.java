package com.royal.elasticsearch.domain.repository;

import com.royal.elasticsearch.domain.Novel;
import com.royal.elasticsearch.infrastructure.domainRepository.DocumentDomainRepository;

import java.util.UUID;

public interface NovelDomainRepository extends DocumentDomainRepository<Novel, UUID> {
    void deleteById(UUID novelId);
}
