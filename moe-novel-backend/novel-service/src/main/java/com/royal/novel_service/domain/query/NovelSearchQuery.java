package com.royal.novel_service.domain.query;

import com.evo.common.query.PagingQuery;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class NovelSearchQuery extends PagingQuery {
    private UUID id;
    private NovelStatus novelStatus;
    private String authorName;
    private String genre;
    private String tag;
}
