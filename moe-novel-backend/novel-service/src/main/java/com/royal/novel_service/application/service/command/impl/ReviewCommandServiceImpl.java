package com.royal.novel_service.application.service.command.impl;

import com.evo.common.exception.ResponseException;
import com.evo.common.webapp.support.SecurityUtils;
import com.royal.novel_service.application.dto.request.review.CreateOrUpdateReviewRequest;
import com.royal.novel_service.application.mapper.CommandMapper;
import com.royal.novel_service.application.service.command.ReviewCommandService;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.Review;
import com.royal.novel_service.domain.command.CreateOrUpdateReviewCmd;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.domain.repository.ReviewDomainRepository;
import com.royal.novel_service.infrastructure.support.exceptions.BadRequestError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewCommandServiceImpl implements ReviewCommandService {
    private final ReviewDomainRepository reviewDomainRepository;
    private final NovelDomainRepository novelDomainRepository;
    private final CommandMapper commandMapper;

    @Override
    public void create(UUID novelId, CreateOrUpdateReviewRequest request) {
        Novel novel = this.novelDomainRepository.findById(novelId).orElseThrow(()-> new ResponseException(BadRequestError.NOVEL_NOT_FOUND));
        CreateOrUpdateReviewCmd cmd = commandMapper.from(request);
        cmd.setUserId(SecurityUtils.getCurrentUserLoginId().get());
        Review review = new Review(cmd);

    }

    @Override
    public void update(CreateOrUpdateReviewRequest request) {

    }

    @Override
    public void delete(UUID reviewId) {

    }
}
