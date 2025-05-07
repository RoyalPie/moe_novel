package com.evo.common.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileResponse {
    private UUID id;
    private String originName;
    private String md5Name;
    private String fileType;
    private String mimeType;
    private Long fileSize;
    private String url;
    private Boolean isPublic;
}
