package com.evo.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum FCMTopic {
    PROMOTIONS("promotions"),
    FLASH_SALES("flash-sales"),
    SYSTEM_NOTIFICATION("system-notification"),
    ;
    private final String topicName;

    public static final List<String> getAllTopics() {
        return Stream.of(FCMTopic.values()).map(FCMTopic::getTopicName).collect(Collectors.toList());
    }
}