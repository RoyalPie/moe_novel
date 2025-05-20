package com.royal.elasticsearch.domain;

import com.royal.elasticsearch.domain.command.SyncNovelCmd;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.List;
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
    private UUID coverImage;
    private int totalChapters;
    private int totalViews;
    private Instant updatedAt;

    private List<String> novelGenres;
    private List<String> novelTags;

    public Novel(SyncNovelCmd cmd) {
        this.novelId = cmd.getNovelId();
        this.title = cmd.getTitle();
        this.coverImage = cmd.getCoverImage();
        this.totalChapters = cmd.getTotalChapters();
        this.totalViews = cmd.getTotalViews();
        this.updatedAt = cmd.getUpdatedAt();

        this.novelGenres = cmd.getNovelGenres();
        this.novelTags = cmd.getNovelTags();
    }

    public void update(SyncNovelCmd cmd) {
        if (cmd.getTitle() != null) {
            this.title = cmd.getTitle();
        }
        if (cmd.getCoverImage() != null) {
            this.coverImage = cmd.getCoverImage();
        }
        if (cmd.getTotalChapters() != this.totalChapters) {
            this.totalChapters = cmd.getTotalChapters();
        }
        if (cmd.getTotalViews() != this.totalViews) {
            this.totalViews = cmd.getTotalViews();
        }
        if (cmd.getUpdatedAt() != null) {
            this.updatedAt = cmd.getUpdatedAt();
        }
        if (cmd.getNovelGenres() != null) {
            this.novelGenres = cmd.getNovelGenres();
        }
        if (cmd.getNovelTags() != null) {
            this.novelTags = cmd.getNovelTags();
        }
    }
}
