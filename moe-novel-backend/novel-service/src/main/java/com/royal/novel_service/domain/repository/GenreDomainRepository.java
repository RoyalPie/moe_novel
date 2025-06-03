package com.royal.novel_service.domain.repository;

import com.evo.common.domainRepository.DomainRepository;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.query.SearchGenreQuery;

import java.util.List;
import java.util.UUID;

public interface GenreDomainRepository extends DomainRepository<Genre, UUID> {
    Genre findByName(String name);

    List<Genre> search(SearchGenreQuery searchGenreQuery);

    List<Genre> getAll();

    Boolean exitsByName(String genreName);

    Long count(SearchGenreQuery searchGenreQuery);
}
