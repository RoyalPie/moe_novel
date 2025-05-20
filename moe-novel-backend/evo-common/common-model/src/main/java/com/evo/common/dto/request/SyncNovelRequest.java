package com.evo.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncNovelRequest {
    private UUID novelId;
    private String title;
    private UUID coverImage;
    private int totalChapters;
    private int totalViews;
    private Instant updatedAt;

    private List<String> novelGenres;
    private List<String> novelTags;
}
