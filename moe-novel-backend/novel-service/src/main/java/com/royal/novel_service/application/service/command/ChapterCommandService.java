package com.royal.novel_service.application.service.command;

import com.royal.novel_service.application.dto.request.chapter.CreateOrUpdateChapterRequest;
import com.royal.novel_service.application.dto.response.ChapterDTO;

import java.util.UUID;

public interface ChapterCommandService {
    ChapterDTO createChapter(CreateOrUpdateChapterRequest request);
    ChapterDTO updateChapter(CreateOrUpdateChapterRequest request);
    void deleteChapter(UUID chapterId);
}
