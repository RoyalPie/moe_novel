package com.royal.novel_service.application.dto.request.novel;

import com.evo.common.dto.request.Request;
import com.royal.novel_service.domain.command.novel.CreateNovelChapterCmd;
import com.royal.novel_service.domain.command.novel.CreateNovelGenreCmd;
import com.royal.novel_service.domain.command.novel.CreateNovelTagCmd;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateNovelRequest extends Request {
    private String title;
    private String authorName;
    private String description;
    private NovelStatus status;
    private UUID coverImage;

    private List<CreateNovelGenreCmd> novelGenres;
    private List<CreateNovelTagCmd> novelTags;
    private List<CreateNovelChapterCmd> novelChapters;
}
