package com.royal.novel_service.application.dto.response;

import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NovelDTO {
    private UUID novelId;
    private String title;
    private String authorName;
    private String description;
    private UUID coverImage;
    private NovelStatus status;
    private int totalChapters;
    private int totalViews;
    private int totalFollows;
}
