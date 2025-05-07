package com.royal.elasticsearch.domain.repository;

import com.royal.elasticsearch.domain.User;
import com.royal.elasticsearch.infrastructure.domainRepository.DocumentDomainRepository;

import java.util.UUID;

public interface UserDomainRepository extends DocumentDomainRepository<User, UUID> {
    void deleteById(UUID userId);
}
