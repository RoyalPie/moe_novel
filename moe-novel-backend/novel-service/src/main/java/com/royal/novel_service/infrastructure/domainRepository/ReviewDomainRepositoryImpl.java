package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Review;
import com.royal.novel_service.domain.repository.ReviewDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.ReviewEnity;
import com.royal.novel_service.infrastructure.persistence.mapper.ReviewEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.ReviewEntityRepository;

import java.util.Optional;
import java.util.UUID;

public class ReviewDomainRepositoryImpl extends AbstractDomainRepository<Review, ReviewEnity, UUID>
        implements ReviewDomainRepository {
    private final ReviewEntityMapper reviewEntityMapper;
    private final ReviewEntityRepository reviewEntityRepository;

    public ReviewDomainRepositoryImpl(ReviewEntityMapper reviewEntityMapper, ReviewEntityRepository reviewEntityRepository){
        super(reviewEntityRepository, reviewEntityMapper);
        this.reviewEntityMapper = reviewEntityMapper;
        this.reviewEntityRepository = reviewEntityRepository;
    }

    @Override
    public Review findByName(String name) {
        return null;
    }

    @Override
    public Optional<Review> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Review getById(UUID uuid) {
        return reviewEntityMapper.toDomainModel(reviewEntityRepository.findById(uuid).get());
    }
}
