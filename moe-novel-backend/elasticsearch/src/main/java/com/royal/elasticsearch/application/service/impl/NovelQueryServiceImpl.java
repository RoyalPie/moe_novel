package com.royal.elasticsearch.application.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import com.royal.elasticsearch.application.dto.request.SearchNovelRequest;
import com.royal.elasticsearch.application.dto.response.SearchNovelResponse;
import com.royal.elasticsearch.application.dto.response.SearchUserResponse;
import com.royal.elasticsearch.application.service.NovelQueryService;
import com.royal.elasticsearch.infrastructure.persistence.document.NovelDocument;
import com.royal.elasticsearch.infrastructure.persistence.document.UserDocument;
import com.royal.elasticsearch.infrastructure.persistence.mapper.NovelDocumentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelQueryServiceImpl implements NovelQueryService {
    private final ElasticsearchOperations elasticsearchOperations;
    private final NovelDocumentMapper documentMapper;

    @Override
    public SearchNovelResponse searchNovel(SearchNovelRequest request) {
        Query query = Query.of(q -> q
                .bool(b -> b
                        .should(s -> s.matchPhrasePrefix(m -> m
                                .field("title")
                                .query(request.getKeyword())
                        ))
                        .should(s -> s.match(m -> m
                                .field("title")
                                .query(request.getKeyword())
                                .fuzziness("AUTO")
                        ))
                )
        );
        NativeQuery searchQuery = NativeQuery.builder()
                .withQuery(query)
                .withPageable(PageRequest.of(
                        request.getPage(),
                        request.getSize(),
                        Sort.by(
                                request.getSortDirection().equalsIgnoreCase("desc")
                                        ? Sort.Direction.DESC
                                        : Sort.Direction.ASC,
                                request.getSortField())))
                .build();

        SearchHits<NovelDocument> searchHits = elasticsearchOperations.search(searchQuery, NovelDocument.class);

        List<NovelDocument> novelDocuments = searchHits.getSearchHits().stream().map(SearchHit::getContent).toList();

        return SearchNovelResponse.builder()
                .novels(documentMapper.toDomainModelList(novelDocuments))
                .totalElements(searchHits.getTotalHits())
                .totalPages((int) Math.ceil((double) searchHits.getTotalHits() / request.getSize()))
                .pageIndex(request.getPage())
                .hasNext((request.getPage() * 1L) * request.getSize() < searchHits.getTotalHits())
                .hasPrevious(request.getPage() > 0)
                .build();
    }
}
