package com.royal.elasticsearch.application.service;

import com.royal.elasticsearch.application.dto.request.SearchNovelRequest;
import com.royal.elasticsearch.application.dto.response.SearchNovelResponse;

public interface NovelQueryService {
    SearchNovelResponse searchNovel(SearchNovelRequest request);

}
