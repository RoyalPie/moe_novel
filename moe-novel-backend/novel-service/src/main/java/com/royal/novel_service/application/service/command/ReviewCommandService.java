package com.royal.novel_service.application.service.command;

import com.royal.novel_service.application.dto.request.review.CreateOrUpdateReviewRequest;

import java.util.UUID;

public interface ReviewCommandService {
    void create(UUID novelId, CreateOrUpdateReviewRequest request);

    void update(CreateOrUpdateReviewRequest request);

    void delete(UUID reviewId);
}
