package com.royal.novel_service.infrastructure.domainRepository;

import com.evo.common.domainRepository.AbstractDomainRepository;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.query.NovelSearchQuery;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.infrastructure.persistence.entity.NovelEntity;
import com.royal.novel_service.infrastructure.persistence.entity.NovelGenreEntity;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelEntityMapper;
import com.royal.novel_service.infrastructure.persistence.mapper.NovelGenreEntityMapper;
import com.royal.novel_service.infrastructure.persistence.repository.NovelEntityRepository;
import com.royal.novel_service.infrastructure.persistence.repository.NovelGenreEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class NovelDomainRepositoryImpl extends AbstractDomainRepository<Novel, NovelEntity, UUID>
        implements NovelDomainRepository {
    private final NovelEntityMapper novelEntityMapper;
    private final NovelEntityRepository novelEntityRepository;
    private final NovelGenreEntityRepository novelGenreEntityRepository;
    private final NovelGenreEntityMapper novelGenreEntityMapper;

    public NovelDomainRepositoryImpl(NovelEntityMapper novelEntityMapper,
                                     NovelEntityRepository novelEntityRepository,
                                     NovelGenreEntityRepository novelGenreEntityRepository,
                                     NovelGenreEntityMapper novelGenreEntityMapper) {
        super(novelEntityRepository, novelEntityMapper);
        this.novelEntityRepository = novelEntityRepository;
        this.novelEntityMapper = novelEntityMapper;
        this.novelGenreEntityRepository = novelGenreEntityRepository;
        this.novelGenreEntityMapper = novelGenreEntityMapper;
    }

    @Override
    public Optional<Novel> findById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    @Transactional
    public Novel save(Novel domainModel) {
        NovelEntity novelEntity = novelEntityMapper.toEntity(domainModel);
        novelEntity = novelEntityRepository.save(novelEntity);

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
    public int getChapterCount(String novelName) {
        return novelEntityRepository.chapterCount(novelName);
    }

    @Override
    public List<Novel> search(NovelSearchQuery novelSearchQuery) {
        List<NovelEntity> novelEntities = novelEntityRepository.search(novelSearchQuery);
        return this.enrichList(novelEntityMapper.toDomainModelList(novelEntities));
    }

    @Override
    public Novel getByNovelName(String novelName) {
        NovelEntity novelEntity = novelEntityRepository.findByNovelName(novelName).orElseThrow(() -> new RuntimeException("Novel not found"));
        return this.enrich(novelEntityMapper.toDomainModel(novelEntity));
    }

    @Override
    public List<Novel> getAll() {
        List<NovelEntity> novels = novelEntityRepository.findAll();
        return this.enrichList(novelEntityMapper.toDomainModelList(novels));
    }

    @Override
    public boolean existsByNovelNameAndAuthorName(String novelName, String authorName) {
        return novelEntityRepository.existsByTitleAndAuthorName(novelName, authorName);
    }

    @Override
    public Long count(NovelSearchQuery novelSearchQuery) {
        return novelEntityRepository.count(novelSearchQuery);
    }

    @Override
    public List<String> getAllName() {
        return novelEntityRepository.getAllName();
    }

    @Override
    protected List<Novel> enrichList(List<Novel> novels) {
        if (novels.isEmpty()) return novels;

        List<UUID> novelIds = novels.stream().map(Novel::getNovelId).toList();

        Map<UUID, List<NovelGenre>> novelGenreMap = novelGenreEntityRepository.findByNovelIdIn(novelIds).stream()
                .map(novelGenreEntityMapper::toDomainModel)
                .collect(Collectors.groupingBy(NovelGenre::getNovelId));

        novels.forEach(novel -> novel.setNovelGenres(novelGenreMap.getOrDefault(novel.getNovelId(), new ArrayList<>())));

        return novels;
    }
}
