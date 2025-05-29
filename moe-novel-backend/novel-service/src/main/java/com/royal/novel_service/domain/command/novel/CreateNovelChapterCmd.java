package com.royal.novel_service.domain.command.novel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateNovelChapterCmd {
    private UUID novelId;
    private UUID chapterId;
}
