package com.royal.novel_service.application.service.query.impl;

import com.royal.novel_service.application.dto.mapper.GenreDTOMapper;
import com.royal.novel_service.application.dto.response.GenreDTO;
import com.royal.novel_service.application.service.query.GenreQueryService;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreQueryServiceImpl implements GenreQueryService {
    private final GenreDomainRepository genreDomainRepository;
    private final GenreDTOMapper genreDTOMapper;

    @Override
    public List<GenreDTO> getAll() {
        return genreDomainRepository.getAll().stream()
                .map(genreDTOMapper::domainModelToDTO).toList();
    }
}
