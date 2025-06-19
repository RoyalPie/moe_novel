package com.royal.novel_service.application.service.command;

import com.royal.novel_service.application.dto.request.chapter.CreateOrUpdateChapterRequest;

import java.util.UUID;

public interface ChapterCommandService {
    void create(UUID novelId, CreateOrUpdateChapterRequest request);

    void update(CreateOrUpdateChapterRequest request);

    void delete(UUID chapterId);
}
