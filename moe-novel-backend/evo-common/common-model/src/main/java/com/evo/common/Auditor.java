package com.evo.common;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class Auditor {
    protected String createdBy;
    protected String lastModifiedBy;
    protected Instant createdAt;
}
