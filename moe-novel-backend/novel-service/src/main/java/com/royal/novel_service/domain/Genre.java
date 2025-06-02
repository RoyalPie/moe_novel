package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateOrUpdateGenreCmd;
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
public class Genre {
    private UUID genreId;
    private String genreName;
    private boolean deleted;

    public Genre(CreateOrUpdateGenreCmd cmd) {
        this.genreId = IdUtils.newUUID();
        this.genreName = cmd.getGenreName();
        this.deleted = false;
    }

    public void update(CreateOrUpdateGenreCmd cmd) {
        this.genreName = cmd.getGenreName();
    }

    public void delete() {
        this.deleted = true;
    }
}
