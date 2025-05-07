package com.royal.elasticsearch.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncUserCmd {
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
    private boolean deleted;
}
