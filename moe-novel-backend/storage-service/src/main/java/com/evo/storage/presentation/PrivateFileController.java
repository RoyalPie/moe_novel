package com.evo.storage.presentation;

import com.evo.common.dto.response.ApiResponses;
import com.evo.common.dto.response.FileResponse;
import com.evo.storage.application.dto.request.SearchFileRequest;
import com.evo.storage.application.dto.request.UpdateFileRequest;
import com.evo.storage.application.service.FileCommandService;
import com.evo.storage.application.service.FileQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PrivateFileController {
    private final FileCommandService fileCommandService;
    private final FileQueryService fileQueryService;

    @PreAuthorize("hasPermission(null, 'file.create')")
    @PostMapping("/file/upload")
    public ApiResponses<List<FileResponse>> storeFile(@RequestPart List<MultipartFile> files) {
        List<FileResponse> fileResponses = fileCommandService.storeFile(files, false);
        return ApiResponses.<List<FileResponse>>builder()
                .data(fileResponses)
                .success(true)
                .code(201)
                .message("Files stored successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PatchMapping("/file/upload")
    public ApiResponses<List<FileResponse>> storeAvatar(@RequestPart List<MultipartFile> files) {
        List<FileResponse> fileResponses = fileCommandService.storeFile(files, false);
        return ApiResponses.<List<FileResponse>>builder()
                .data(fileResponses)
                .success(true)
                .code(201)
                .message("Files stored successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasPermission(null, 'file.view')")
    @GetMapping("/file/view-image/{fileId}")
    public ResponseEntity<Resource> serveImage(@PathVariable UUID fileId,
                                               @RequestParam(required = false) Double ratio,
                                               @RequestParam(required = false) Integer width,
                                               @RequestParam(required = false) Integer height) throws IOException {
        Resource resource = fileQueryService.serveImage(fileId, width, height, ratio);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    @PreAuthorize("hasPermission(null, 'file.read')")
    @GetMapping("/file/{filedId}")
    public ApiResponses<FileResponse> getFile(@PathVariable UUID filedId) {
        FileResponse fileResponse = fileQueryService.getPrivateFile(filedId);
        return ApiResponses.<FileResponse>builder()
                .data(fileResponse)
                .success(true)
                .code(200)
                .message("File retrieved successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasPermission(null, 'file.admin')")
    @GetMapping("/file")
    public ApiResponses<List<FileResponse>> searchFiles(@RequestBody SearchFileRequest searchFileRequest) {
        return fileQueryService.search(searchFileRequest);
    }

    @PreAuthorize("hasPermission(null, 'file.update')")
    @PutMapping("/update")
    public ApiResponses<FileResponse> updateFile(@RequestBody UpdateFileRequest updateFileRequest) {
        FileResponse fileResponse = fileCommandService.updateFile(updateFileRequest);
        return ApiResponses.<FileResponse>builder()
                .data(fileResponse)
                .success(true)
                .code(200)
                .message("File updated successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PreAuthorize("hasPermission(null, 'file.delete')")
    @DeleteMapping("/delete/{fileId}")
    public ApiResponses<Void> deleteFile(@PathVariable UUID fileId) {
        fileCommandService.deleteFile(fileId);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(204)
                .message("File deleted successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}