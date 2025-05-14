package com.royal.novel_service.domain.command;

import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateNovelCmd {
    private UUID novelId;
    private String title;
    private String authorName;
    private String description;
    private UUID coverImage;
    private NovelStatus novelStatus;
    private int totalChapters;

    private List<CreateNovelGenreCmd> novelGenres;
    private List<CreateNovelTagCmd> novelTags;
    private List<CreateNovelChapterCmd> novelChapters;
}
