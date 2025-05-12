package com.royal.novel_service.domain.command;

import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateNovelCmd {
    private String title;
    private String authorName;
    private String description;
    private NovelStatus status;

    private List<CreateNovelGenreCmd> novelGenres;
    private List<CreateNovelTagCmd> novelTags;
    private List<CreateNovelChapterCmd> novelChapters;
}
