package com.evo.common.dto.event;

import com.evo.common.dto.request.SyncNovelRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncNovelEvent {
    private String syncAction;
    private SyncNovelRequest syncNovelRequest;
}
