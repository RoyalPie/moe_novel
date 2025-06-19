package com.royal.novel_service.domain.command.chapter;

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
    private UUID novelId;
    private int chapterNumber;
    private String content;
}
