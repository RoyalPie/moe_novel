package com.royal.novel_service.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateOrUpdateChapterCmd {
    private UUID chapterId;
    private String title;
    private int chapterNumber;
    private String content;
}
