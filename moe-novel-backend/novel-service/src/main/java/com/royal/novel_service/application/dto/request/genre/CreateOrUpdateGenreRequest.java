package com.royal.novel_service.application.dto.request.genre;

import com.evo.common.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateGenreRequest extends Request {
    private String genreName;
}
