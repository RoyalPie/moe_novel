package com.royal.novel_service.application.dto.request.tag;

import com.evo.common.dto.request.PagingRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryTagRequest extends PagingRequest {
    private String keyword;
}
