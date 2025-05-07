package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateOrUpdateChapterCmd;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Chapter {
    private UUID chapterId;
    private String title;
    private int chapterNumber;
    private String content;

    public Chapter(CreateOrUpdateChapterCmd cmd) {
        this.chapterId = IdUtils.newUUID();
        this.title = cmd.getTitle();
        this.chapterNumber = cmd.getChapterNumber();
        this.content = cmd.getContent();
    }

    public void update(CreateOrUpdateChapterCmd cmd) {
        this.title = cmd.getTitle();
        this.chapterNumber = cmd.getChapterNumber();
        this.content = cmd.getContent();
    }
}
