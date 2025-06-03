package com.royal.novel_service.application.service.command;

import com.royal.novel_service.application.dto.request.tag.CreateOrUpdateTagRequest;

import java.util.UUID;

public interface TagCommandService {
    void create(CreateOrUpdateTagRequest request);

    void update(UUID tagId, CreateOrUpdateTagRequest request);

    void delete(UUID tagId);
}
