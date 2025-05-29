package com.royal.novel_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChapterDTO {
    private UUID chapterId;
    private String title;
    private int chapterNumber;
    private String content;
}
