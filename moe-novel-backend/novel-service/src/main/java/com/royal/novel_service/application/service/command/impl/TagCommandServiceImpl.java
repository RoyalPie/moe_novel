package com.royal.novel_service.application.service.command.impl;

import com.evo.common.exception.ResponseException;
import com.royal.novel_service.application.dto.request.tag.CreateOrUpdateTagRequest;
import com.royal.novel_service.application.mapper.CommandMapper;
import com.royal.novel_service.application.service.command.TagCommandService;
import com.royal.novel_service.domain.Tag;
import com.royal.novel_service.domain.command.CreateOrUpdateTagCmd;
import com.royal.novel_service.domain.repository.TagDomainRepository;
import com.royal.novel_service.infrastructure.support.exceptions.BadRequestError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TagCommandServiceImpl implements TagCommandService {
    private final TagDomainRepository tagDomainRepository;
    private final CommandMapper commandMapper;

    @Override
    public void create(CreateOrUpdateTagRequest request) {
        if (this.tagDomainRepository.exitsByName(request.getTagName())) {
            throw new ResponseException(BadRequestError.TAG_EXITS);
        }
        CreateOrUpdateTagCmd cmd = commandMapper.from(request);
        Tag tag = new Tag(cmd);
        tagDomainRepository.save(tag);
    }

    @Override
    public void update(CreateOrUpdateTagRequest request) {
        if (this.tagDomainRepository.exitsByName(request.getTagName())) {
            throw new ResponseException(BadRequestError.TAG_EXITS);
        }
        CreateOrUpdateTagCmd cmd = commandMapper.from(request);
        Tag tag = tagDomainRepository.getById(request.getTagId());
        tag.update(cmd);
        tagDomainRepository.save(tag);
    }

    @Override
    public void delete(UUID tagId) {
        if (this.tagDomainRepository.findById(tagId).isEmpty()) {
            throw new ResponseException(BadRequestError.TAG_NOT_FOUND);
        }
        tagDomainRepository.delete(tagId);
    }
}
