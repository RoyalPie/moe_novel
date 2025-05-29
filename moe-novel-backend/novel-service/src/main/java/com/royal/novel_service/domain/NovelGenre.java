package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.novel.CreateNovelGenreCmd;
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
public class NovelGenre {
    private UUID id;
    private UUID novelId;
    private UUID genreId;

    public NovelGenre(CreateNovelGenreCmd cmd) {
        this.id = IdUtils.newUUID();
        this.novelId = cmd.getNovelId();
        this.genreId = cmd.getGenreId();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        NovelGenre that = (NovelGenre) o;
        return Objects.equals(novelId, that.novelId) && Objects.equals(genreId, that.genreId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(novelId, genreId);
    }
}
