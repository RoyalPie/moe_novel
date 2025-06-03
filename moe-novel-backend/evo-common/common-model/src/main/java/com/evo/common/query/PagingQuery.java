package com.evo.common.query;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
public class PagingQuery extends Query {
    public static final String ASC_SYMBOL = "asc";
    public static final String DESC_SYMBOL = "desc";
    protected int pageIndex;
    protected int pageSize;
    protected String sortBy;
    protected List<UUID> ids;
    private String keyword;

}
