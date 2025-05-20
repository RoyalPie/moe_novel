package com.royal.elasticsearch.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncNovelCmd {
    private UUID novelId;
    private String title;
    private UUID coverImage;
    private int totalChapters;
    private int totalViews;
    private Instant updatedAt;

    private List<String> novelGenres;
    private List<String> novelTags;
}
