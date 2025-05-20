package com.royal.elasticsearch.application.service;

import com.royal.elasticsearch.domain.command.SyncUserCmd;

import java.util.UUID;

public interface UserCommandService {
    void create(SyncUserCmd cmd);

    void update(SyncUserCmd cmd);

    void delete(UUID userId);
}
