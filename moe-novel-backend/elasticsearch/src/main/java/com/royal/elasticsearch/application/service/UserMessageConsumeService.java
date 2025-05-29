package com.royal.elasticsearch.application.service;

import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.enums.SyncActionType;
import com.royal.elasticsearch.application.mapper.CommandMapper;
import com.royal.elasticsearch.domain.command.SyncUserCmd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserMessageConsumeService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserCommandService userCommandService;
    private final CommandMapper commandMapper;

    @KafkaListener(topics = "sync-user", groupId = "iam-group")
    public void syncUser(SyncUserEvent syncUserEvent){
        SyncUserCmd cmd = commandMapper.from(syncUserEvent.getSyncUserRequest());
        SyncActionType actionType = syncUserEvent.getSyncAction();
        switch (actionType) {
            case CREATED:
                userCommandService.create(cmd);
                break;
            case UPDATED:
                userCommandService.update(cmd);
                break;
            case DELETED:
                userCommandService.delete(cmd.getUserId());
                break;
            default:
                log.error("Invalid sync user action: {}", syncUserEvent.getSyncAction());
        }

    }
}
