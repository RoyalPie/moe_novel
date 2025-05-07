package com.evo.storage.application.service.impl;

import com.evo.common.dto.response.FileResponse;
import com.evo.common.dto.response.PageApiResponse;
import com.evo.storage.application.dto.mapper.FileResponseMapper;
import com.evo.storage.application.dto.request.SearchFileRequest;
import com.evo.storage.application.mapper.QueryMapper;
import com.evo.storage.application.service.FileQueryService;
import com.evo.storage.domain.File;
import com.evo.storage.domain.query.SearchFileQuery;
import com.evo.storage.domain.repository.FileDomainRepository;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileQueryServiceImpl implements FileQueryService {
    private final FileDomainRepository fileDomainRepository;
    private final FileResponseMapper fileResponseMapper;
    private final QueryMapper queryMapper;

    @Value("${file.storage.path}")
    private String storageLocation;

    @Override
    public PageApiResponse<List<FileResponse>> search(SearchFileRequest request) {
        SearchFileQuery searchFileQuery = queryMapper.from(request);
        List<File> files = fileDomainRepository.search(searchFileQuery);
        Long totalFiles = fileDomainRepository.count(searchFileQuery);
        List <FileResponse> fileResponses = files.stream().map(fileResponseMapper::domainModelToDTO).toList();
        PageApiResponse.PageableResponse pageableResponse = PageApiResponse.PageableResponse.builder()
                .pageSize(request.getPageSize())
                .pageIndex(request.getPageIndex())
                .totalElements(totalFiles)
                .totalPages((int)(Math.ceil((double)totalFiles / request.getPageSize())))
                .hasNext(((request.getPageIndex() + 1L) * request.getPageSize() < totalFiles))
                .hasPrevious(request.getPageIndex() >0).build();
        return PageApiResponse.<List<FileResponse>>builder()
                .data(fileResponses)
                .success(true)
                .code(200)
                .pageable(pageableResponse)
                .message("Search File successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @Override
    public FileResponse getPrivateFile(UUID filedId) {
        File file = fileDomainRepository.getById(filedId);
        String url = "http://localhost:8083/api/uploads/private/" + file.getMd5Name();
        FileResponse fileResponse = fileResponseMapper.domainModelToDTO(file);
        fileResponse.setUrl(url);
        return fileResponse;
    }

    @Override
    public FileResponse getPublicFile(UUID filedId) {
        File file = fileDomainRepository.getById(filedId);
        String url = "http://localhost:8083/api/uploads/public/" + file.getMd5Name();
        FileResponse fileResponse = fileResponseMapper.domainModelToDTO(file);
        fileResponse.setUrl(url);
        return fileResponse;
    }
    @Override
    public Resource serveImage(UUID fileId, Integer width, Integer height, Double ratio) throws IOException {
        String fileName = fileDomainRepository.getById(fileId).getMd5Name();
        Path path = Paths.get(storageLocation).resolve(fileName);
        BufferedImage originalImage = ImageIO.read(path.toFile());

        // Resize the image using your resizeImage method (you can adjust the parameters as needed)
        BufferedImage resizedImage = resizeImage(originalImage, width, height, ratio);  // Example resize to 300x300

        // Convert the resized image into a byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "JPEG", baos);
        baos.flush();
        byte[] imageData = baos.toByteArray();
        baos.close();

        // Create a ByteArrayResource to return
        return new ByteArrayResource(imageData);
    }

    private BufferedImage resizeImage(BufferedImage originalImage, Integer width, Integer height, Double ratio) {
        int newWidth = (width != null) ? width : originalImage.getWidth();
        int newHeight = (height != null) ? height : originalImage.getHeight();

        if (ratio != null) {
            newWidth = (int) (originalImage.getWidth() * ratio);
            newHeight = (int) (originalImage.getHeight() * ratio);
        }

        return Scalr.resize(originalImage, Scalr.Method.BALANCED, newWidth, newHeight);
    }
}
