package com.royal.novel_service.presentation.rest;

import com.evo.common.dto.response.ApiResponses;
import com.royal.novel_service.application.dto.request.novel.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.novel.UpdateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.service.command.NovelCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

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
    @PatchMapping("/novel/${novelId}")
    public ApiResponses<NovelDTO> updateNovel(@RequestBody UpdateNovelRequest request,
                                              @RequestParam UUID novelId) {
        NovelDTO novelDTO = novelCommandService.updateNovel(request, novelId);
        return ApiResponses.<NovelDTO>builder()
                .data(novelDTO)
                .success(true)
                .code(200)
                .message("Update novel successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
    @DeleteMapping("novel/${novelId}")
    public ApiResponses<NovelDTO> moveToTrashBin(@PathVariable UUID novelId){
        NovelDTO novelDTO = novelCommandService.deleteNovel(novelId);
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
