package com.royal.novel_service.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CreateOrUpdateGenreCmd {
    private UUID genreId;
    private String genreName;
}
