package com.royal.novel_service.presentation.rest;

import com.evo.common.dto.response.Response;
import com.royal.novel_service.application.dto.request.genre.CreateOrUpdateGenreRequest;
import com.royal.novel_service.application.dto.request.tag.CreateOrUpdateTagRequest;
import com.royal.novel_service.application.dto.response.GenreDTO;
import com.royal.novel_service.application.dto.response.TagDTO;
import com.royal.novel_service.application.service.command.TagCommandService;
import com.royal.novel_service.application.service.query.TagQueryService;
import com.royal.novel_service.domain.repository.TagDomainRepository;
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
public class TagController {
    private final TagCommandService tagCommandService;
    private final TagQueryService tagQueryService;
    @GetMapping("/tag")
    public Response<List<TagDTO>> getGenres() {
        return Response.of(tagQueryService.getAll());
    }

    @PostMapping("/tag/create")
    public Response<Void> createGenre(@RequestBody CreateOrUpdateTagRequest request) {
        tagCommandService.create(request);
        return Response.ok();
    }

    @PatchMapping("/tag/{id}")
    public Response<Void> updateGenre(@PathVariable("id") UUID id, @RequestBody CreateOrUpdateTagRequest request) {
        tagCommandService.update(id, request);
        return Response.ok();
    }
}
