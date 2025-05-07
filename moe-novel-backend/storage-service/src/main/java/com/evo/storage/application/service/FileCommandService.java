package com.evo.storage.application.service;

import com.evo.common.dto.response.FileResponse;
import com.evo.storage.application.dto.request.UpdateFileRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface FileCommandService {
    List<FileResponse> storeFile(List<MultipartFile> files, boolean isPublic);
    FileResponse updateFile(UpdateFileRequest updateFileRequest);
    void deleteFile(UUID fileId);
}
