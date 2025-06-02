package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.query.SearchGenreQuery;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.GenreEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.GenreEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.GenreEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class GenreDomainRepositoryImpl extends AbstractDomainRepository<Genre, GenreEntity, UUID>
        implements GenreDomainRepository {
    private final GenreEntityMapper entityMapper;
    private final GenreEntityRepository repository;

    public GenreDomainRepositoryImpl(GenreEntityMapper entityMapper, GenreEntityRepository repository) {
        super(repository, entityMapper);
        this.entityMapper = entityMapper;
        this.repository = repository;
    }

    @Override
    public Optional<Genre> findById(UUID id) {
        return this.repository.findById(id)
                .map(this.entityMapper::toDomainModel);
    }

    @Override
    public Genre getById(UUID id) {
        return null;
    }

    @Override
    public Genre findByName(String name) {
        return entityMapper.toDomainModel(repository.findByGenreName(name));
    }

    @Override
    public List<Genre> search(SearchGenreQuery searchGenreQuery) {
        return null;
    }

    @Override
    public Boolean exitsByName(String genreName) {
        return repository.existsByGenreNameAndDeletedFalse(genreName);
    }

    @Override
    public Long count(SearchGenreQuery searchGenreQuery) {
        return null;
    }
}
