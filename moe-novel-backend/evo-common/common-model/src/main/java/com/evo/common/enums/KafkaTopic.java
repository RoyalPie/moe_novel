package com.evo.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum KafkaTopic {
    SYNC_USER("sync-user"),
    SYNC_NOVEL("sync-novel")
    ;
    private final String topicName;
}
