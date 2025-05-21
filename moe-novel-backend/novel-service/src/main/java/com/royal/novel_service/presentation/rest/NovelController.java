package com.royal.novel_service.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.royal.novel_service.application.dto.request.CreateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.service.NovelCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NovelController {
    private final NovelCommandService novelCommandService;

    @GetMapping("/novel/create")
    public ApiResponses<NovelDTO> createNovel(@RequestBody CreateNovelRequest request) {
        NovelDTO novelDTO = novelCommandService.createNovel(request);
        return ApiResponses.<NovelDTO>builder()
                .data(novelDTO)
                .success(true)
                .code(201)
                .message("Create novel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
