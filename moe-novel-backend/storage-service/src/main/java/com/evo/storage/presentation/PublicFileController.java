package com.evo.storage.presentation;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.FileResponse;
import com.evo.storage.application.service.FileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PublicFileController {
    private final FileQueryService fileQueryService;

    @GetMapping("/public/file/{filedId}")
    public ApiResponses<FileResponse> getFile(@PathVariable UUID filedId) {
        FileResponse fileResponse = fileQueryService.getPublicFile(filedId);
        return ApiResponses.<FileResponse>builder()
                .data(fileResponse)
                .success(true)
                .code(200)
                .message("File retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}