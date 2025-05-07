package com.royal.novel_service.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateNovelChapterCmd {
    private UUID novelId;
    private UUID chapterId;
}
