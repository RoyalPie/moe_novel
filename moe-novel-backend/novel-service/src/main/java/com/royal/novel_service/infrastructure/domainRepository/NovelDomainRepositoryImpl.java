package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.NovelChapter;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.query.SearchNovelQuery;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.NovelChapterEntity;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import com.royal.novel_service.infrastructure.persistence.entity.NovelGenreEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelChapterEntityMapper;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelEntityMapper;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelGenreEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.NovelChapterEntityRepository;
import com.royal.novel_service.infrastructure.persistence.repository.NovelEntityRepository;
import com.royal.novel_service.infrastructure.persistence.repository.NovelGenreEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NovelDomainRepositoryImpl extends AbstractDomainRepository<Novel, NovelEntity, UUID>
        implements NovelDomainRepository {
    private final NovelEntityMapper novelEntityMapper;
    private final NovelEntityRepository novelEntityRepository;
    private final NovelChapterEntityRepository novelChapterEntityRepository;
    private final NovelChapterEntityMapper novelChapterEntityMapper;
    private final NovelGenreEntityRepository novelGenreEntityRepository;
    private final NovelGenreEntityMapper novelGenreEntityMapper;

    public NovelDomainRepositoryImpl(NovelEntityMapper novelEntityMapper,
                                     NovelEntityRepository novelEntityRepository,
                                     NovelChapterEntityRepository novelChapterEntityRepository,
                                     NovelChapterEntityMapper novelChapterEntityMapper,
                                     NovelGenreEntityRepository novelGenreEntityRepository,
                                     NovelGenreEntityMapper novelGenreEntityMapper) {
        super(novelEntityRepository, novelEntityMapper);
        this.novelEntityRepository = novelEntityRepository;
        this.novelEntityMapper = novelEntityMapper;
        this.novelChapterEntityRepository = novelChapterEntityRepository;
        this.novelChapterEntityMapper = novelChapterEntityMapper;
        this.novelGenreEntityRepository = novelGenreEntityRepository;
        this.novelGenreEntityMapper = novelGenreEntityMapper;
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

        List<NovelGenreEntity> novelGenreEntities = domainModel.getNovelGenres().stream()
                .map(novelGenreEntityMapper::toEntity)
                .toList();
        novelGenreEntityRepository.saveAll(novelGenreEntities);

        return novelEntityMapper.toDomainModel(novelEntity);
    }

    @Override
    public Novel getById(UUID uuid) {
        return null;
    }

    @Override
    public int getChapterCount(String novelName){
        return novelEntityRepository.chapterCount(novelName);
    }
    @Override
    public List<Novel> search(SearchNovelQuery searchNovelQuery){
        List<NovelEntity> novelEntities = novelEntityRepository.search(searchNovelQuery);
        return this.enrichList(novelEntityMapper.toDomainModelList(novelEntities));
    }

    @Override
    public Novel getByNovelName(String novelName){
        NovelEntity novelEntity = novelEntityRepository.findByNovelName(novelName).orElseThrow(() -> new RuntimeException("Novel not found"));
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

    @Override
    protected List<Novel> enrichList(List<Novel> novels) {
        if (novels.isEmpty()) return novels;

        List<UUID> novelIds = novels.stream().map(Novel::getNovelId).toList();

        Map<UUID, List<NovelChapter>> novelChapterMap = novelChapterEntityRepository.findByNovelIdIn(novelIds).stream()
                .map(novelChapterEntityMapper::toDomainModel)
                .collect(Collectors.groupingBy(NovelChapter::getNovelId));

        novels.forEach(novel -> novel.setNovelChapters(novelChapterMap.getOrDefault(novel.getNovelId(), new ArrayList<>())));

        Map<UUID, List<NovelGenre>> novelGenreMap = novelGenreEntityRepository.findByNovelIdIn(novelIds).stream()
                .map(novelGenreEntityMapper::toDomainModel)
                .collect(Collectors.groupingBy(NovelGenre::getNovelId));

        novels.forEach(novel -> novel.setNovelGenres(novelGenreMap.getOrDefault(novel.getNovelId(), new ArrayList<>())));

        return novels;
    }
}
