package com.royal.iam_service.domain.command;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LockUserCmd {
    private boolean enabled;
}
