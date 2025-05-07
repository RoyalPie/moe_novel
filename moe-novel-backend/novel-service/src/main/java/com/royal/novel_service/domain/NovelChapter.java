package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateNovelChapterCmd;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class NovelChapter {
    private UUID id;
    private UUID novelId;
    private UUID chapterId;

    public NovelChapter(CreateNovelChapterCmd cmd) {
        this.id = IdUtils.newUUID();
        this.novelId = cmd.getNovelId();
        this.chapterId = cmd.getChapterId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NovelChapter that = (NovelChapter) o;
        return Objects.equals(novelId, that.novelId) && Objects.equals(chapterId, that.chapterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(novelId, chapterId);
    }
}
