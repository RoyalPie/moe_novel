package com.royal.novel_service.application.mapper;

import com.royal.novel_service.application.dto.request.chapter.CreateOrUpdateChapterRequest;
import com.royal.novel_service.application.dto.request.genre.CreateOrUpdateGenreRequest;
import com.royal.novel_service.application.dto.request.novel.CreateNovelRequest;
import com.royal.novel_service.application.dto.request.novel.UpdateNovelRequest;
import com.royal.novel_service.application.dto.request.review.CreateOrUpdateReviewRequest;
import com.royal.novel_service.application.dto.request.tag.CreateOrUpdateTagRequest;
import com.royal.novel_service.domain.command.CreateOrUpdateGenreCmd;
import com.royal.novel_service.domain.command.CreateOrUpdateReviewCmd;
import com.royal.novel_service.domain.command.CreateOrUpdateTagCmd;
import com.royal.novel_service.domain.command.chapter.CreateOrUpdateChapterCmd;
import com.royal.novel_service.domain.command.novel.CreateNovelCmd;
import com.royal.novel_service.domain.command.novel.UpdateNovelCmd;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommandMapper {
    CreateNovelCmd from(CreateNovelRequest request);

    UpdateNovelCmd from(UpdateNovelRequest request);

    CreateOrUpdateChapterCmd from(CreateOrUpdateChapterRequest request);

    CreateOrUpdateGenreCmd from(CreateOrUpdateGenreRequest request);

    CreateOrUpdateTagCmd from(CreateOrUpdateTagRequest request);

    CreateOrUpdateReviewCmd from(CreateOrUpdateReviewRequest request);

}
