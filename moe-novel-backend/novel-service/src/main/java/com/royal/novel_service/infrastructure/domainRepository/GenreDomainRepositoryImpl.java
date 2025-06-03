package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.query.SearchGenreQuery;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.GenreEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.GenreEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.GenreEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GenreDomainRepositoryImpl extends AbstractDomainRepository<Genre, GenreEntity, UUID>
        implements GenreDomainRepository {
    private final GenreEntityMapper genreEntityMapper;
    private final GenreEntityRepository genreEntityRepository;

    public GenreDomainRepositoryImpl(GenreEntityMapper entityMapper, GenreEntityRepository repository) {
        super(repository, entityMapper);
        this.genreEntityMapper = entityMapper;
        this.genreEntityRepository = repository;
    }

    @Override
    public Optional<Genre> findById(UUID id) {
        return this.genreEntityRepository.findById(id)
                .map(this.genreEntityMapper::toDomainModel);
    }

    @Override
    public Genre getById(UUID id) {
        return null;
    }

    @Override
    public Genre findByName(String name) {
        return genreEntityMapper.toDomainModel(genreEntityRepository.findByGenreName(name));
    }

    @Override
    public List<Genre> search(SearchGenreQuery searchGenreQuery) {
        return Collections.emptyList();
    }

    @Override
    public List<Genre> getAll() {
        return genreEntityRepository.findAll().stream().map(genreEntityMapper::toDomainModel).toList();
    }

    @Override
    public Boolean exitsByName(String genreName) {
        return genreEntityRepository.existsByGenreNameAndDeletedFalse(genreName);
    }

    @Override
    public Long count(SearchGenreQuery searchGenreQuery) {
        return null;
    }
}
