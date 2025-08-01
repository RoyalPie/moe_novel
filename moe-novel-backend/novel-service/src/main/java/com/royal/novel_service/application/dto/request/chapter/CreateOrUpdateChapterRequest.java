package com.royal.novel_service.application.dto.request.chapter;

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
public class CreateOrUpdateChapterRequest extends Request {
    private UUID novelId;
    private UUID chapterId;
    private String title;
    private int chapterNumber;
    private String content;
}
