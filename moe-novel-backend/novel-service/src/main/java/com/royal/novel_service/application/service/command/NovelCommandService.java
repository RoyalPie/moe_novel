package com.royal.novel_service.application.service.command;

import com.royal.novel_service.application.dto.request.novel.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.novel.UpdateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;

import java.util.UUID;

public interface NovelCommandService {
    NovelDTO create(CreateNovelRequest request);

    NovelDTO update(UpdateNovelRequest request, UUID novelId);

    NovelDTO delete(UUID novelId);

}
