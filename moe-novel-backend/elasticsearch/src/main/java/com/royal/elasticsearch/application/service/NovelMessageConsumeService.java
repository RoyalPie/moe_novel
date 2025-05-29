package com.royal.elasticsearch.application.service;

import com.evo.common.dto.event.SyncNovelEvent;
import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.enums.SyncActionType;
import com.royal.elasticsearch.application.mapper.CommandMapper;
import com.royal.elasticsearch.domain.command.SyncNovelCmd;
import com.royal.elasticsearch.domain.command.SyncUserCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NovelMessageConsumeService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final NovelCommandService novelCommandService;
    private final CommandMapper commandMapper;

    @KafkaListener(topics = "sync-novel", groupId = "novel-group")
    public void syncUser(SyncNovelEvent syncNovelEvent){
        SyncNovelCmd cmd = commandMapper.from(syncNovelEvent.getSyncNovelRequest());
        SyncActionType actionType = syncNovelEvent.getSyncAction();
        switch (actionType) {
            case CREATED:
                novelCommandService.create(cmd);
                break;
            case UPDATED:
                novelCommandService.update(cmd);
                break;
            case DELETED:
                novelCommandService.delete(cmd.getNovelId());
                break;
            default:
                log.error("Invalid sync user action: {}", syncNovelEvent.getSyncAction());
        }

    }
}
