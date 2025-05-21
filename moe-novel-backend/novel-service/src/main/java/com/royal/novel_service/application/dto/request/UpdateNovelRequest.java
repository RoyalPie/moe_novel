package com.royal.novel_service.application.dto.request;

import com.royal.novel_service.domain.command.CreateNovelChapterCmd;
import com.royal.novel_service.domain.command.CreateNovelGenreCmd;
import com.royal.novel_service.domain.command.CreateNovelTagCmd;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNovelRequest {
    private UUID novelId;
    private String title;
    private String authorName;
    private String description;
    private NovelStatus status;
    private UUID coverImage;

    private List<CreateNovelGenreCmd> novelGenres;
    private List<CreateNovelTagCmd> novelTags;
    private List<CreateNovelChapterCmd> novelChapters;
}
