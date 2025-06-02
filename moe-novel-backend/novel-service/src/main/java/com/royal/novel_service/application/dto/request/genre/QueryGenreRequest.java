package com.royal.novel_service.application.dto.request.genre;

import com.evo.common.dto.request.PagingRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QueryGenreRequest extends PagingRequest {
    private String keyword;
}
