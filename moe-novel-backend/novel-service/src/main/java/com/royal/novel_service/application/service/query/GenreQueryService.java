package com.royal.novel_service.application.service.query;

import com.royal.novel_service.application.dto.response.GenreDTO;

import java.util.List;

public interface GenreQueryService {
    List<GenreDTO> getAll();
}
