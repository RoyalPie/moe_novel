package com.royal.novel_service.application.service.command;

import com.royal.novel_service.application.dto.request.genre.CreateOrUpdateGenreRequest;

import java.util.UUID;

public interface GenreCommandService {
    void create(CreateOrUpdateGenreRequest request);

    void update(UUID genreId, CreateOrUpdateGenreRequest request);

    void delete(UUID genreId);
}
