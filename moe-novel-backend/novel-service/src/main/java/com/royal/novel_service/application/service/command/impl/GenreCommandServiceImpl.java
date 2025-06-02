package com.royal.novel_service.application.service.command.impl;

import com.evo.common.exception.ResponseException;
import com.royal.novel_service.application.dto.request.genre.CreateOrUpdateGenreRequest;
import com.royal.novel_service.application.mapper.CommandMapper;
import com.royal.novel_service.application.service.command.GenreCommandService;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.command.CreateOrUpdateGenreCmd;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import com.royal.novel_service.infrastructure.support.exceptions.BadRequestError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenreCommandServiceImpl implements GenreCommandService {
    private final GenreDomainRepository genreDomainRepository;
    private final CommandMapper commandMapper;

    @Override
    @Transactional
    public void create(CreateOrUpdateGenreRequest request) {
        if (this.genreDomainRepository.exitsByName(request.getGenreName())) {
            throw new ResponseException(BadRequestError.GENRE_EXITS);
        }
        CreateOrUpdateGenreCmd cmd = commandMapper.from(request);
        Genre genre = new Genre(cmd);
        genreDomainRepository.save(genre);
    }

    @Override
    @Transactional
    public void update(UUID genreId, CreateOrUpdateGenreRequest request) {
        if (this.genreDomainRepository.exitsByName(request.getGenreName())) {
            throw new ResponseException(BadRequestError.GENRE_EXITS);
        }
        CreateOrUpdateGenreCmd cmd = commandMapper.from(request);
        if (this.genreDomainRepository.findById(genreId).isEmpty()) {
            throw new ResponseException(BadRequestError.GENRE_NOT_FOUND);
        }
        Genre genre = genreDomainRepository.getById(genreId);
        genre.update(cmd);
        genreDomainRepository.save(genre);
    }

    @Override
    @Transactional
    public void delete(UUID genreId) {
        if (this.genreDomainRepository.findById(genreId).isEmpty()) {
            throw new ResponseException(BadRequestError.GENRE_NOT_FOUND);
        }
        genreDomainRepository.delete(genreId);
    }
}
