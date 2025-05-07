package com.royal.iam_service.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDTO {
    private UUID id;
    private String name;
    private boolean isRoot;
    private boolean isDeleted;
    private String createdBy;
    private String lastModifiedBy;
    private Instant createdAt;
}
