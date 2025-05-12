package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.query.SearchNovelQuery;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.NovelChapterEntity;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelChapterEntityMapper;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.NovelChapterEntityRepository;
import com.royal.novel_service.infrastructure.persistence.repository.NovelEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public class NovelDomainRepositoryImpl extends AbstractDomainRepository<Novel, NovelEntity, UUID>
        implements NovelDomainRepository {
    private final NovelEntityMapper novelEntityMapper;
    private final NovelEntityRepository novelEntityRepository;
    private final NovelChapterEntityRepository novelChapterEntityRepository;
    private final NovelChapterEntityMapper novelChapterEntityMapper;

    public NovelDomainRepositoryImpl(NovelEntityMapper novelEntityMapper,
                                     NovelEntityRepository novelEntityRepository,
                                     NovelChapterEntityRepository novelChapterEntityRepository,
                                     NovelChapterEntityMapper novelChapterEntityMapper) {
        super(novelEntityRepository, novelEntityMapper);
        this.novelEntityRepository = novelEntityRepository;
        this.novelEntityMapper = novelEntityMapper;
        this.novelChapterEntityRepository = novelChapterEntityRepository;
        this.novelChapterEntityMapper = novelChapterEntityMapper;
    }

    @Override
    @Transactional
    public Novel save(Novel domainModel) {
        NovelEntity novelEntity = novelEntityMapper.toEntity(domainModel);
        novelEntity = novelEntityRepository.save(novelEntity);

        List<NovelChapterEntity> novelChapterEntities = domainModel.getNovelChapters().stream()
                .map(novelChapterEntityMapper::toEntity)
                .toList();
        novelChapterEntityRepository.saveAll(novelChapterEntities);

        return novelEntityMapper.toDomainModel(novelEntity);
    }

    @Override
    public Novel getById(UUID uuid) {
        return null;
    }
    @Override
    public List<Novel> search(SearchNovelQuery searchNovelQuery){
        List<NovelEntity> novelEntities = novelEntityRepository.search(searchNovelQuery);
        return this.enrichList(novelEntityMapper.toDomainModelList(novelEntities));
    }

    @Override
    public Novel getByNovelName(String novelName){
        NovelEntity novelEntity = novelEntityRepository.findByUsername(novelName).orElseThrow(() -> new RuntimeException("Novel not found"));
        return this.enrich(novelEntityMapper.toDomainModel(novelEntity));
    }

    @Override
    public List<Novel> getAll(){
        List<NovelEntity> novels = novelEntityRepository.findAll();
        return this.enrichList(novelEntityMapper.toDomainModelList(novels));
    }

    @Override
    public boolean existsByNovelName(String novelName){
        return novelEntityRepository.existsByTitle(novelName);
    }

    @Override
    public Long count(SearchNovelQuery searchNovelQuery){
        return novelEntityRepository.count(searchNovelQuery);
    }

    @Override
    public List<String> getAllName(){
        return novelEntityRepository.getAllName();
    }
}
