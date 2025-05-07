package com.royal.iam_service.infrastructure.adapter.storage;


import com.evo.common.dto.response.FileResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    List<FileResponse> uploadFile(List<MultipartFile> files);
    void kafkaUloadFile();
//    FileResponse getFile(UUID fileId);
//    FileResponse updateFile(UpdateFileRequest updateFileRequest);
//    void deleteFile(UUID fileId);
//    List<FileResponse> search(SearchFileRequest searchFileRequest);
//    ApiResponses<Void> testRetry();
}
