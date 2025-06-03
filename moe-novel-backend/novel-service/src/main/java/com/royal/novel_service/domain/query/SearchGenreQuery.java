package com.royal.novel_service.domain.query;

import com.evo.common.query.PagingQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class SearchGenreQuery extends PagingQuery {
    private String keyword;
}
