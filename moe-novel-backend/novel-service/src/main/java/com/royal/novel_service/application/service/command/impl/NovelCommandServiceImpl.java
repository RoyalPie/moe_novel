package com.royal.novel_service.application.service.command.impl;

import com.evo.common.dto.event.SyncNovelEvent;
import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.dto.request.SyncNovelRequest;
import com.evo.common.dto.request.SyncUserRequest;
import com.evo.common.enums.KafkaTopic;
import com.evo.common.enums.SyncActionType;
import com.royal.novel_service.application.dto.mapper.NovelDTOMapper;
import com.royal.novel_service.application.dto.mapper.SyncMapper;
import com.royal.novel_service.application.dto.request.novel.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.novel.UpdateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.mapper.CommandMapper;
import com.royal.novel_service.application.service.command.NovelCommandService;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.NovelTag;
import com.royal.novel_service.domain.Tag;
import com.royal.novel_service.domain.command.novel.CreateNovelCmd;
import com.royal.novel_service.domain.command.novel.UpdateNovelCmd;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.domain.repository.TagDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NovelCommandServiceImpl implements NovelCommandService {
    private final NovelDomainRepository novelDomainRepository;
    private final TagDomainRepository tagDomainRepository;
    private final GenreDomainRepository genreDomainRepository;
    private final CommandMapper commandMapper;
    private final SyncMapper syncMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NovelDTOMapper novelDTOMapper;

    @Override
    public NovelDTO createNovel(CreateNovelRequest request) {
        CreateNovelCmd cmd = commandMapper.from(request);
        Novel novel = new Novel(cmd);
        novelDomainRepository.save(novel);
        SyncNovelRequest syncNovelRequest = syncMapper.from(novel, null, null);
        SyncNovelEvent syncNovelEvent = SyncNovelEvent.builder()
                .syncAction(SyncActionType.CREATED)
                .syncNovelRequest(syncNovelRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_NOVEL.getTopicName(), syncNovelEvent);
        return novelDTOMapper.domainModelToDTO(novel);
    }

    @Override
    public NovelDTO updateNovel(UpdateNovelRequest request, UUID novelId){
        UpdateNovelCmd cmd = commandMapper.from(request);
        Novel novel = novelDomainRepository.getById(novelId);
        novel.update(cmd);
        List<String> genres = genreDomainRepository.findAllByIds(novel.getNovelGenres().stream().map(NovelGenre::getGenreId).toList()).stream().map(Genre::getGenreName).toList();
        List<String> tags = tagDomainRepository.findAllByIds(novel.getNovelTags().stream().map(NovelTag::getTagId).toList()).stream().map(Tag::getTagName).toList();
        SyncNovelRequest syncNovelRequest = syncMapper.from(novel, genres, tags);
        SyncNovelEvent syncNovelEvent = SyncNovelEvent.builder()
                .syncAction(SyncActionType.UPDATED)
                .syncNovelRequest(syncNovelRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_NOVEL.getTopicName(), syncNovelEvent);
        return novelDTOMapper.domainModelToDTO(novelDomainRepository.save(novel));
    }

    @Override
    public NovelDTO deleteNovel(UUID novelId) {
        Novel novel = novelDomainRepository.getById(novelId);
        novel.setDeleted(true);
        SyncNovelRequest syncNovelRequest = syncMapper.from(novel, null, null);
        SyncNovelEvent syncNovelEvent = SyncNovelEvent.builder()
                .syncAction(SyncActionType.DELETED)
                .syncNovelRequest(syncNovelRequest)
                .build();
        kafkaTemplate.send(KafkaTopic.SYNC_NOVEL.getTopicName(), syncNovelEvent);
        return novelDTOMapper.domainModelToDTO(novelDomainRepository.save(novel));
    }

}
