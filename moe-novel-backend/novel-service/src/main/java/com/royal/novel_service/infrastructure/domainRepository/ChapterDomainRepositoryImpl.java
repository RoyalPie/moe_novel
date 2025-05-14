package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.domain.repository.ChapterDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.ChapterEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.ChapterEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.ChapterEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ChapterDomainRepositoryImpl extends AbstractDomainRepository<Chapter, ChapterEntity, UUID>
        implements ChapterDomainRepository {
    private final ChapterEntityMapper entityMapper;
    private final ChapterEntityRepository repository;

    public ChapterDomainRepositoryImpl(ChapterEntityRepository repository, ChapterEntityMapper entityMapper) {
        super(repository, entityMapper);
        this.repository = repository;
        this.entityMapper = entityMapper;
    }


    @Override
    public List<Chapter> findByChapterNumberAndTilte(String novelTitle, int chapterNumber) {
        List<ChapterEntity> chapterEntities = repository.findByChapterNumber(novelTitle, chapterNumber);
        return entityMapper.toDomainModelList(chapterEntities);
    }

    @Override
    public Chapter getById(UUID uuid) {
        return entityMapper.toDomainModel(repository.findById(uuid).orElseThrow(() -> new RuntimeException("Chapter not found")));
    }
}
