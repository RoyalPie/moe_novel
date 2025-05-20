package com.royal.novel_service.application.service;

import com.royal.novel_service.application.dto.response.NovelDTO;
import org.springframework.stereotype.Service;

@Service
public interface NovelQueryService {
    NovelDTO loadNovels();
}
