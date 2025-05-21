package com.royal.novel_service.application.dto.request;

import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.NovelTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNovelRequest {
    private String title;
    private String authorName;
    private String description;
    private UUID coverImage;

    private List<NovelGenre> novelGenres;
    private List<NovelTag> novelTags;
}
