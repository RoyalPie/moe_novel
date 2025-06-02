package com.royal.novel_service.application.dto.request.chapter;

import com.evo.common.dto.request.PagingRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class QueryChapterRequest extends PagingRequest {
    private UUID chapterId;
}
