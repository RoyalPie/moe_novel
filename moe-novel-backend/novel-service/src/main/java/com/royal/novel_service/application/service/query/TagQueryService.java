package com.royal.novel_service.application.service.query;

import com.royal.novel_service.application.dto.response.TagDTO;

import java.util.List;

public interface TagQueryService {
    List<TagDTO> getAll();
}
