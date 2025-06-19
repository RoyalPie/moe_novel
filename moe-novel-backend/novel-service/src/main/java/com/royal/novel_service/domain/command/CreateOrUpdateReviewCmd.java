package com.royal.novel_service.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateOrUpdateReviewCmd {
    private UUID reviewId;
    private UUID userId;
    private UUID novelId;
    private int rating;
    private String content;
}
