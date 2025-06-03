package com.royal.novel_service.application.service.query;

import com.evo.common.dto.PageDTO;
import com.royal.novel_service.application.dto.request.novel.NovelSearchRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import org.springframework.stereotype.Service;

@Service
public interface NovelQueryService {
    PageDTO<NovelDTO> search(NovelSearchRequest request);
}
