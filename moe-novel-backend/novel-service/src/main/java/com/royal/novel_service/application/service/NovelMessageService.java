package com.royal.novel_service.application.service;

import com.evo.common.dto.event.SyncNovelEvent;
import com.evo.common.dto.request.SyncNovelRequest;
import com.evo.common.enums.KafkaTopic;
import com.evo.common.enums.SyncActionType;
import com.royal.novel_service.application.dto.mapper.SyncMapper;
import com.royal.novel_service.domain.Genre;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.repository.GenreDomainRepository;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.domain.repository.TagDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Service
public class NovelMessageService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NovelDomainRepository novelDomainRepository;
    private final GenreDomainRepository genreDomainRepository;
    private final TagDomainRepository tagDomainRepository;
    private final SyncMapper syncMapper;

    public void syncNovel() {
        List<Novel> novels = novelDomainRepository.getAll();
        for (Novel novel : novels) {
            List<String> genres = genreDomainRepository.findAllByIds(novel.getNovelGenres().stream().map(NovelGenre::getGenreId).toList()).stream().map(Genre::getGenreName).toList();
            List<String> tags = null;

            SyncNovelRequest request = syncMapper.from(novel, genres, tags);
            SyncNovelEvent syncNovelEvent = SyncNovelEvent.builder()
                    .syncAction(SyncActionType.CREATED)
                    .syncNovelRequest(request)
                    .build();
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(KafkaTopic.SYNC_NOVEL.getTopicName(), syncNovelEvent);
            future.whenComplete((result, ex) -> {
                if (ex == null) {
                    System.out.println("Sent message=[" + result +
                            "] with offset=[" + result.getRecordMetadata().offset() + "]");
                } else {
                    System.out.println("Unable to send message=[" +
                            request + "] due to : " + ex.getMessage());
                }
            });
        }
    }
}
