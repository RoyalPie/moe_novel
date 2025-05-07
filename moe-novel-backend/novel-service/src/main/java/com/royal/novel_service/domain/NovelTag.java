package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateNovelTagCmd;
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
public class NovelTag {
    private UUID id;
    private UUID novelId;
    private UUID tagId;

    public NovelTag(CreateNovelTagCmd cmd) {
        this.id = IdUtils.newUUID();
        this.novelId = cmd.getNovelId();
        this.tagId = cmd.getTagId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NovelTag that = (NovelTag) o;
        return Objects.equals(novelId, that.novelId) && Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(novelId, tagId);
    }
}
