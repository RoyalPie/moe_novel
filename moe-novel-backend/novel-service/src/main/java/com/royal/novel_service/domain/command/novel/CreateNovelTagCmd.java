package com.royal.novel_service.domain.command.novel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateNovelTagCmd {
    private UUID novelId;
    private UUID tagId;
}
