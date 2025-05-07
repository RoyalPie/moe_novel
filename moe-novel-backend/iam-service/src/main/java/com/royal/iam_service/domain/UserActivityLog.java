package com.royal.iam_service.domain;

import com.royal.iam_service.domain.command.WriteLogCmd;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class UserActivityLog {
    private UUID id;
    private String activity;

    public UserActivityLog(WriteLogCmd cmd) {
        this.activity = cmd.getActivity();
    }
}
