package com.royal.elasticsearch.domain;

import com.royal.elasticsearch.domain.command.SyncUserCmd;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.UUID;

@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter
@Getter
public class User {
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

    public User(SyncUserCmd cmd) {
        this.userId = cmd.getUserId();
        this.keycloakUserId = cmd.getKeycloakUserId();
        this.username = cmd.getUsername();
        this.email = cmd.getEmail();
        this.firstName = cmd.getFirstName();
        this.lastName = cmd.getLastName();
        this.dateOfBirth = cmd.getDateOfBirth();
        this.phoneNumber = cmd.getPhoneNumber();
        this.avatarFileId = cmd.getAvatarFileId();
        this.active = cmd.isActive();
        this.deleted = cmd.isDeleted();
    }

    public void update(SyncUserCmd cmd) {
        if (cmd.getEmail() != null) {
            this.email = cmd.getEmail();
        }
        if (cmd.getFirstName() != null) {
            this.firstName = cmd.getFirstName();
        }
        if (cmd.getLastName() != null) {
            this.lastName = cmd.getLastName();
        }
        if (cmd.getAvatarFileId() != null) {
            this.avatarFileId = cmd.getAvatarFileId();
        }
        if (cmd.getDateOfBirth() != null) {
            this.dateOfBirth = cmd.getDateOfBirth();
        }
        if (cmd.getAvatarFileId() != null) {
            this.phoneNumber = cmd.getPhoneNumber();
        }
        if (cmd.isActive() != false) {
            this.active = cmd.isActive();
        }
        if (cmd.isDeleted() != false) {
            this.deleted = cmd.isDeleted();
        }
    }
}

