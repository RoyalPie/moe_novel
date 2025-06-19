package com.royal.iam_service.presentation.rest;

import com.evo.common.dto.event.SyncUserEvent;
import com.evo.common.dto.request.SyncUserRequest;
import com.evo.common.enums.SyncActionType;
import com.royal.iam_service.application.mapper.SyncMapper;
import com.royal.iam_service.domain.User;
import com.royal.iam_service.domain.repository.UserDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UserDomainRepository userDomainRepository;
    private final SyncMapper syncMapper;

    @PostMapping("/test")
    public void test(Authentication authentication) {

        List<User> users = userDomainRepository.getAll();
        for (User user : users) {
            SyncUserRequest request = syncMapper.from(user);
            SyncUserEvent syncUserEvent = SyncUserEvent.builder()
                    .syncAction(SyncActionType.CREATED)
                    .syncUserRequest(request)
                    .build();
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send("sync-user", syncUserEvent);
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
