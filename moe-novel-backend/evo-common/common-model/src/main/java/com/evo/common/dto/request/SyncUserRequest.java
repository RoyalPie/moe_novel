package com.evo.common.dto.request;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SyncUserRequest {
    private UUID userId;
    private UUID keycloakUserId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UUID avatarFileId;
    private Date dateOfBirth;
    private String phoneNumber;
    private boolean active;
}
