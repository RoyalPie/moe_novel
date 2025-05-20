package com.royal.elasticsearch.application.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.royal.elasticsearch.domain.Novel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@SuperBuilder
public class SearchNovelResponse {
    List<Novel> novels;
    private int pageIndex;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean hasNext;
    private boolean hasPrevious;
}
