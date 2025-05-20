package com.royal.elasticsearch.application.service.impl;

import com.royal.elasticsearch.application.service.NovelCommandService;
import com.royal.elasticsearch.domain.Novel;
import com.royal.elasticsearch.domain.command.SyncNovelCmd;
import com.royal.elasticsearch.domain.repository.NovelDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NovelCommandServiceImpl implements NovelCommandService{
    private final NovelDomainRepository novelDomainRepository;
    @Override
    public void create(SyncNovelCmd cmd) {
        Novel novel = new Novel(cmd);
        novelDomainRepository.save(novel);
    }

    @Override
    public void update(SyncNovelCmd cmd) {
        Novel novel = novelDomainRepository.getById(cmd.getNovelId());
        novel.update(cmd);
        novelDomainRepository.save(novel);
    }

    @Override
    public void delete(UUID novelId) {
        novelDomainRepository.deleteById(novelId);
    }
}
