package com.royal.novel_service.application.dto.request.novel;

import com.evo.common.dto.request.Request;
import com.royal.novel_service.domain.NovelGenre;
import com.royal.novel_service.domain.NovelTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateNovelRequest extends Request {
    private String title;
    private UUID authorId;
    private String authorName;
    private String description;
    private UUID coverImage;

    private List<NovelGenre> novelGenres;
    private List<NovelTag> novelTags;
}
