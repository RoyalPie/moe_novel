package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateNovelCmd;
import com.royal.novel_service.domain.command.CreateNovelGenreCmd;
import com.royal.novel_service.domain.command.CreateNovelTagCmd;
import com.royal.novel_service.domain.command.UpdateNovelCmd;
import com.royal.novel_service.infrastructure.support.enums.NovelStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Novel {
    private UUID novelId;
    private String title;
    private String authorName;
    private String description;
    private UUID coverImage;
    private NovelStatus status;
    private int totalViews;
    private int totalFollows;

    private List<NovelGenre> novelGenres;
    private List<NovelTag> novelTags;
    private List<NovelReview> novelReviews;
    private List<NovelChapter> novelChapters;

    public Novel(CreateNovelCmd cmd) {
        this.novelId = IdUtils.newUUID();
        this.title = cmd.getTitle();
        this.authorName = cmd.getAuthorName();
        this.description = cmd.getDescription();
        this.coverImage = cmd.getCoverImage();
        this.status = cmd.getNovelStatus();
        this.totalViews = 0;
        this.totalFollows = 0;
        this.novelGenres = new ArrayList<>();
        this.novelTags = new ArrayList<>();

        if (cmd.getNovelGenres() != null) {
            cmd.getNovelGenres().forEach(createNovelGenrecmd -> {
                createNovelGenrecmd.setNovelId(this.novelId);
                novelGenres.add(new NovelGenre(createNovelGenrecmd));
            });
        }

        if (cmd.getNovelTags() != null) {
            cmd.getNovelTags().forEach(createNovelTagcmd -> {
                createNovelTagcmd.setNovelId(this.novelId);
                novelTags.add(new NovelTag(createNovelTagcmd));
            });
        }

    }

    public void update(UpdateNovelCmd cmd) {
        if (cmd.getTitle() != null) {
            this.title = cmd.getTitle();
        }
        if (cmd.getAuthorName() != null) {
            this.authorName = cmd.getAuthorName();
        }
        if (cmd.getDescription() != null) {
            this.description = cmd.getDescription();
        }
        if (cmd.getStatus() != null) {
            this.status = cmd.getStatus();
        }
        if (cmd.getNovelTags() != null && !cmd.getNovelTags().isEmpty()) {
            if (this.novelTags == null) {
                this.novelTags = new ArrayList<>();
            }

            // Map existing roles by tagId
            Map<UUID, NovelTag> existingTagsMap = new HashMap<>();
            for (NovelTag tag : this.novelTags) {
                existingTagsMap.put(tag.getTagId(), tag);
            }

            // Update or add new tags
            for (CreateNovelTagCmd novelTagCmd : cmd.getNovelTags()) {
                UUID tagId = novelTagCmd.getTagId();
                novelTagCmd.setNovelId(this.novelId);
                if (!existingTagsMap.containsKey(tagId)) {
                    this.novelTags.add(new NovelTag(novelTagCmd));
                }
            }
        }

        if (cmd.getNovelGenres() != null && !cmd.getNovelGenres().isEmpty()) {
            if (this.novelGenres == null) {
                this.novelGenres = new ArrayList<>();
            }

            // Map existing roles by genreId
            Map<UUID, NovelGenre> existingGenresMap = new HashMap<>();
            for (NovelGenre genre : this.novelGenres) {
                existingGenresMap.put(genre.getGenreId(), genre);
            }

            // Update or add new genres
            for (CreateNovelGenreCmd novelGenreCmd : cmd.getNovelGenres()) {
                UUID genreId = novelGenreCmd.getGenreId();
                novelGenreCmd.setNovelId(this.novelId);
                if (!existingGenresMap.containsKey(genreId)) {
                    this.novelGenres.add(new NovelGenre(novelGenreCmd));
                }
            }
        }
    }

    public void changeCoverImage(UUID fileId){
        this.coverImage = fileId;
    }
}
