package com.royal.novel_service.application.service.command.impl;

import com.evo.common.exception.ResponseException;
import com.royal.novel_service.application.dto.mapper.ChapterDTOMapper;
import com.royal.novel_service.application.dto.request.chapter.CreateOrUpdateChapterRequest;
import com.royal.novel_service.application.dto.response.ChapterDTO;
import com.royal.novel_service.application.mapper.CommandMapper;
import com.royal.novel_service.application.service.command.ChapterCommandService;
import com.royal.novel_service.domain.Chapter;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.command.chapter.CreateOrUpdateChapterCmd;
import com.royal.novel_service.domain.command.novel.CreateNovelChapterCmd;
import com.royal.novel_service.domain.command.novel.UpdateNovelCmd;
import com.royal.novel_service.domain.repository.ChapterDomainRepository;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import com.royal.novel_service.infrastructure.support.exceptions.BadRequestError;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChapterCommandServiceImpl implements ChapterCommandService {
    private final CommandMapper commandMapper;
    private final ChapterDTOMapper chapterDTOMapper;
    private final ChapterDomainRepository chapterDomainRepository;
    private final NovelDomainRepository novelDomainRepository;
    @Override
    public void create(UUID novelId, CreateOrUpdateChapterRequest request) {
        Novel novel = novelDomainRepository.findById(novelId).orElseThrow(()-> new ResponseException(BadRequestError.NOVEL_NOT_FOUND));
        CreateOrUpdateChapterCmd createChapterCmd = commandMapper.from(request);
        Chapter newChapter = new Chapter(createChapterCmd);
        UpdateNovelCmd updateNovelCmd = new UpdateNovelCmd();
        CreateNovelChapterCmd createNovelChapterCmd = new CreateNovelChapterCmd(novel.getNovelId(), newChapter.getChapterId());
        List<CreateNovelChapterCmd> cmds = new ArrayList<>();
        cmds.add(createNovelChapterCmd);
        updateNovelCmd.setChapters(cmds);
        novel.update(updateNovelCmd);
        novelDomainRepository.save(novel);

    }

    @Override
    public void update(CreateOrUpdateChapterRequest request) {
        CreateOrUpdateChapterCmd updateChapterCmd = commandMapper.from(request);
        Chapter chapter = chapterDomainRepository.getById(updateChapterCmd.getChapterId());
        chapter.update(updateChapterCmd);
        chapterDomainRepository.save(chapter);
    }

    @Override
    public void delete(UUID chapterId) {
        chapterDomainRepository.delete(chapterId);
    }
}
