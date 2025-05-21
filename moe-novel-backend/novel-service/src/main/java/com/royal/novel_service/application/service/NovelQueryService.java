package com.royal.novel_service.application.service;

import com.royal.novel_service.application.dto.response.NovelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NovelQueryService {
    List<NovelDTO> loadNovels(int novelNumbers);
}
