package com.royal.novel_service.application.service.impl.command;

import com.royal.novel_service.application.dto.mapper.NovelDTOMapper;
import com.royal.novel_service.application.dto.request.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.UpdateNovelRequest;
import com.royal.novel_service.application.dto.response.NovelDTO;
import com.royal.novel_service.application.mapper.CommandMapper;
import com.royal.novel_service.application.service.NovelCommandService;
import com.royal.novel_service.domain.Novel;
import com.royal.novel_service.domain.command.CreateNovelCmd;
import com.royal.novel_service.domain.command.UpdateNovelCmd;
import com.royal.novel_service.domain.repository.NovelDomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NovelCommandServiceImpl implements NovelCommandService {
    private final NovelDomainRepository novelDomainRepository;
    private final CommandMapper commandMapper;
    private final NovelDTOMapper novelDTOMapper;

    @Override
    public NovelDTO createNovel(CreateNovelRequest request) {
        CreateNovelCmd cmd = commandMapper.from(request);
        Novel novel = new Novel(cmd);
        novelDomainRepository.save(novel);
        return novelDTOMapper.domainModelToDTO(novel);
    }

    @Override
    public NovelDTO updateNovel(UpdateNovelRequest request){
        UpdateNovelCmd cmd = commandMapper.from(request);
        Novel novel = novelDomainRepository.getById(request.getNovelId());
        novel.update(cmd);
        return novelDTOMapper.domainModelToDTO(novelDomainRepository.save(novel));
    }

}
