package com.royal.novel_service.application.service.query.impl;

import com.royal.novel_service.application.dto.mapper.NovelDTOMapper;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.service.query.NovelQueryService;
import com.royal.novel_service.domain.Novel;
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

    @Override
    public List<NovelDTO> loadNovels(int novelNumber){
        List<Novel> novels = novelDomainRepository.getAll();
        List<NovelDTO> dtos = new ArrayList<>();
        for(Novel novel:novels){
            dtos.add(novelDTOMapper.domainModelToDTO(novel));
        }
        return dtos;
    }
}
