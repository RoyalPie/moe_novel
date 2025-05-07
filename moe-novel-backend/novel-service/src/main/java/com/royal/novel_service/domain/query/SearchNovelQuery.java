package com.royal.novel_service.domain.query;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchNovelQuery extends PagingQuery {
    private String keyword;
}
