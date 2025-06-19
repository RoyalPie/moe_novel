package com.royal.novel_service.application.dto.request.review;

import com.evo.common.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateReviewRequest extends Request {
    private UUID reviewId;
    private int rating;
    private String content;

}
