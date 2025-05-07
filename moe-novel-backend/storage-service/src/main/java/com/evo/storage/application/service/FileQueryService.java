package com.evo.storage.application.service;

import com.evo.common.dto.response.FileResponse;
import com.evo.common.dto.response.PageApiResponse;
import com.evo.storage.application.dto.request.SearchFileRequest;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.UUID;

public interface FileQueryService {
    PageApiResponse<List<FileResponse>> search(SearchFileRequest searchFileRequest);
    FileResponse getPrivateFile(UUID filedId);
    FileResponse getPublicFile(UUID filedId);
    Resource serveImage(UUID fileId, Integer width, Integer height, Double ratio) throws IOException;
}
