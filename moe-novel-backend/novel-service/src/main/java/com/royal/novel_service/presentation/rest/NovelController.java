package com.royal.novel_service.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.royal.novel_service.application.dto.request.novel.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.novel.UpdateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.service.command.NovelCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NovelController {
    private final NovelCommandService novelCommandService;

    @PostMapping("/novel/create")
    public ApiResponses<NovelDTO> createNovel(@RequestBody CreateNovelRequest request) {
        NovelDTO novelDTO = novelCommandService.create(request);
        return ApiResponses.<NovelDTO>builder()
                .data(novelDTO)
                .success(true)
                .code(201)
                .message("Create novel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PutMapping("/novel/{id}")
    public ApiResponses<NovelDTO> updateNovel(@RequestBody UpdateNovelRequest request,
                                              @PathVariable(name = "id") UUID novelId) {
        NovelDTO novelDTO = novelCommandService.update(request, novelId);
        return ApiResponses.<NovelDTO>builder()
                .data(novelDTO)
                .success(true)
                .code(200)
                .message("Update novel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @DeleteMapping("novel/{id}")
    public ApiResponses<NovelDTO> moveToTrashBin(@PathVariable(name = "id") UUID novelId) {
        NovelDTO novelDTO = novelCommandService.delete(novelId);
        return ApiResponses.<NovelDTO>builder()
                .data(novelDTO)
                .success(true)
                .code(200)
                .message("Delete novel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
