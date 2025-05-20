package com.royal.elasticsearch.infrastructure.persistence.repository;

import com.royal.elasticsearch.infrastructure.persistence.document.NovelDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface NovelDocumentRepository extends ElasticsearchRepository<NovelDocument, UUID> {
}
