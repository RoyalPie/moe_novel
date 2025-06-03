package com.royal.novel_service.application.service.query.impl;

import com.royal.novel_service.application.dto.mapper.TagDTOMapper;
import com.royal.novel_service.application.dto.response.TagDTO;
import com.royal.novel_service.application.service.query.TagQueryService;
import com.royal.novel_service.domain.repository.TagDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagQueryServiceImpl implements TagQueryService {
    private final TagDomainRepository tagDomainRepository;
    private final TagDTOMapper tagDTOMapper;

    @Override
    public List<TagDTO> getAll() {
        return tagDomainRepository.getAll().stream()
                .map(tagDTOMapper::domainModelToDTO).toList();
    }
}
