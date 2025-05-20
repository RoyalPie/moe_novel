package com.royal.elasticsearch.application.service;

import com.royal.elasticsearch.domain.command.SyncNovelCmd;

import java.util.UUID;

public interface NovelCommandService {
    void create(SyncNovelCmd cmd);

    void update(SyncNovelCmd cmd);

    void delete(UUID novelId);
}
