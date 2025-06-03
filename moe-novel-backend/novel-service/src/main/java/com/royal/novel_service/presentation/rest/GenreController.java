package com.royal.novel_service.presentation.rest;

import com.evo.common.dto.response.Response;
import com.royal.novel_service.application.dto.request.genre.CreateOrUpdateGenreRequest;
import com.royal.novel_service.application.dto.response.GenreDTO;
import com.royal.novel_service.application.service.command.GenreCommandService;
import com.royal.novel_service.application.service.command.NovelCommandService;
import com.royal.novel_service.application.service.query.GenreQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GenreController {
    private final NovelCommandService novelCommandService;
    private final GenreCommandService genreCommandService;
    private final GenreQueryService genreQueryService;

    @GetMapping("/genre")
    public Response<List<GenreDTO>> getGenres() {
        return Response.of(genreQueryService.getAll());
    }

    @PostMapping("/genre/create")
    public Response<Void> createGenre(@RequestBody CreateOrUpdateGenreRequest request) {
        genreCommandService.create(request);
        return Response.ok();
    }

    @PatchMapping("/genre/{id}")
    public Response<Void> updateGenre(@PathVariable("id") UUID id, @RequestBody CreateOrUpdateGenreRequest request) {
        genreCommandService.update(id, request);
        return Response.ok();
    }

}
