package com.royal.novel_service.domain.repository;

import com.evo.common.domainRepository.DomainRepository;
import com.royal.novel_service.domain.Review;

import java.util.UUID;

public interface ReviewDomainRepository extends DomainRepository<Review, UUID> {
    Review findByName(String name);
}
