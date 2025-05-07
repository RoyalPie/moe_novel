package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateNovelReviewCmd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class NovelReview {
    private UUID id;
    private UUID novelId;
    private UUID reviewId;

    public NovelReview(CreateNovelReviewCmd cmd) {
        this.id = IdUtils.newUUID();
        this.novelId = cmd.getNovelId();
        this.reviewId = cmd.getReviewId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NovelReview that = (NovelReview) o;
        return Objects.equals(novelId, that.novelId) && Objects.equals(reviewId, that.reviewId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(novelId, reviewId);
    }
}
