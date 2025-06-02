package com.royal.novel_service.domain;

import com.evo.common.webapp.support.IdUtils;
import com.royal.novel_service.domain.command.CreateOrUpdateTagCmd;
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
public class Tag {
    private UUID tagId;
    private String tagName;
    private boolean deleted;

    public Tag(CreateOrUpdateTagCmd cmd) {
        this.tagId = IdUtils.newUUID();
        this.tagName = cmd.getTagName();
        this.deleted = false;
    }

    public void update(CreateOrUpdateTagCmd cmd) {
        this.tagName = cmd.getTagName();
    }
}
