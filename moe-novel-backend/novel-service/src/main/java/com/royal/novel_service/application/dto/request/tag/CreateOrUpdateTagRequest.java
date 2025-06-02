package com.royal.novel_service.application.dto.request.tag;

import com.evo.common.dto.request.Request;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrUpdateTagRequest extends Request {
    private UUID tagId;
    private String tagName;
}
