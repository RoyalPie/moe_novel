package com.royal.novel_service.application.service.query.impl;

import com.evo.common.dto.PageDTO;
import com.royal.novel_service.application.dto.mapper.NovelDTOMapper;
import com.royal.novel_service.application.dto.request.novel.NovelSearchRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.mapper.QueryMapper;
import com.royal.novel_service.application.service.query.NovelQueryService;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.query.NovelSearchQuery;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NovelQueryServiceImpl implements NovelQueryService {
    private final NovelDomainRepository novelDomainRepository;
    private final NovelDTOMapper novelDTOMapper;
    private final QueryMapper queryMapper;

    @Override
    public PageDTO<NovelDTO> search(NovelSearchRequest request){
        NovelSearchQuery query = queryMapper.from(request);
        Long total = novelDomainRepository.count(query);
        if (total == 0) return PageDTO.empty();

        List<NovelDTO> novelDTOS = novelDomainRepository
                .search(query)
                .stream().map(novelDTOMapper::domainModelToDTO)
                .toList();

        return PageDTO.of(novelDTOS, request.getPageIndex(), request.getPageSize(), total);
    }
}
