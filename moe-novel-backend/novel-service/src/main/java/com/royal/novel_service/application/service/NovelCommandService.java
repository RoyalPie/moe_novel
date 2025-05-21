package com.royal.novel_service.application.service;

import com.royal.novel_service.application.dto.request.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.UpdateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;

public interface NovelCommandService {
    NovelDTO createNovel(CreateNovelRequest request);
    NovelDTO updateNovel(UpdateNovelRequest request);
}
