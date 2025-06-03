package com.royal.novel_service.application.dto.request.novel;

import com.evo.common.dto.request.PagingRequest;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NovelSearchRequest extends PagingRequest {
    private String keyword;
    private UUID id;
    private NovelStatus novelStatus;
    private String authorName;
    private String genre;
    private String tag;
}
