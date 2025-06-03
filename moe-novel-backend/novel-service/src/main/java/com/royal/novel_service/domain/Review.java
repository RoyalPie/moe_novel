package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateOrUpdateReviewCmd;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Review {
    private UUID reviewId;
    private int rating;
    private Instant createdAt;
    private String content;
    private boolean deleted;

    public Review(CreateOrUpdateReviewCmd cmd) {
        this.reviewId = IdUtils.newUUID();
        this.rating = cmd.getRating();
        this.content = cmd.getContent();
        this.deleted = false;
    }

    public void update(CreateOrUpdateReviewCmd cmd) {
        this.rating = cmd.getRating();
        this.content = cmd.getContent();
    }

    public void delete(){this.deleted = true;}
}
