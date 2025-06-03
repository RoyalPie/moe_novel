package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Tag;
import com.royal.novel_service.domain.query.SearchTagQuery;
import com.royal.novel_service.domain.repository.TagDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.TagEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.TagEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.TagEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class TagDomainRepositoryImpl extends AbstractDomainRepository<Tag, TagEntity, UUID>
        implements TagDomainRepository {
    private final TagEntityMapper tagEntityMapper;
    private final TagEntityRepository tagEntityRepository;

    public TagDomainRepositoryImpl(TagEntityMapper tagEntityMapper, TagEntityRepository tagEntityRepository) {
        super(tagEntityRepository, tagEntityMapper);
        this.tagEntityMapper = tagEntityMapper;
        this.tagEntityRepository = tagEntityRepository;
    }


    @Override
    public Tag findByName(String name) {
        return tagEntityMapper.toDomainModel(tagEntityRepository.findByTagName(name));
    }

    @Override
    public List<Tag> search(SearchTagQuery searchTagQuery) {
        return List.of();
    }

    @Override
    public List<Tag> getAll() {
        return tagEntityRepository.findAllDeletedFalse().stream()
                .map(this.tagEntityMapper::toDomainModel).toList();
    }

    @Override
    public Long count(SearchTagQuery searchTagQuery) {
        return 0L;
    }

    @Override
    public Boolean exitsByName(String name) {
        return tagEntityRepository.existsByTagNameAndDeletedFalse(name);
    }

    @Override
    public Optional<Tag> findById(UUID uuid) {
        return tagEntityRepository.findById(uuid)
                .map(this.tagEntityMapper::toDomainModel);
    }

    @Override
    public Tag getById(UUID uuid) {
        return tagEntityMapper.toDomainModel(tagEntityRepository.findById(uuid).get());
    }
}
