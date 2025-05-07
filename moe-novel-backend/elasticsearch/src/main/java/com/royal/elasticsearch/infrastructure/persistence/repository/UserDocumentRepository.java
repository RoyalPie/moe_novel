package com.royal.elasticsearch.infrastructure.persistence.repository;

import com.royal.elasticsearch.infrastructure.persistence.document.UserDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.UUID;

public interface UserDocumentRepository extends ElasticsearchRepository<UserDocument, UUID> {
}
