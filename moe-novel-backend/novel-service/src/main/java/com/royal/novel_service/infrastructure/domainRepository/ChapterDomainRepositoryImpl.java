package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.domain.repository.ChapterDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.ChapterEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.ChapterEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.ChapterEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ChapterDomainRepositoryImpl extends AbstractDomainRepository<Chapter, ChapterEntity, UUID>
        implements ChapterDomainRepository {
    private final ChapterEntityMapper chapterEntityMapper;
    private final ChapterEntityRepository chapterEntityRepository;

    public ChapterDomainRepositoryImpl(ChapterEntityRepository repository, ChapterEntityMapper entityMapper) {
        super(repository, entityMapper);
        this.chapterEntityRepository = repository;
        this.chapterEntityMapper = entityMapper;
    }

    @Override
    public List<Chapter> findByChapterNumberAndTitle(String novelTitle, int chapterNumber) {
        List<ChapterEntity> chapterEntities = chapterEntityRepository.findByChapterNumber(novelTitle, chapterNumber);
        return chapterEntityMapper.toDomainModelList(chapterEntities);
    }

    @Override
    public Optional<Chapter> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public Chapter getById(UUID uuid) {
        return chapterEntityMapper.toDomainModel(chapterEntityRepository.findById(uuid).orElseThrow(() -> new RuntimeException("Chapter not found")));
    }
}
