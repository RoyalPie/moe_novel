package com.royal.iam_service.domain;

import com.royal.iam_service.domain.command.CreateUserCmd;
import com.royal.iam_service.domain.command.CreateUserRoleCmd;
import com.royal.iam_service.domain.command.UpdateUserCmd;
import com.evo.common.webapp.support.IdUtils;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.*;

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
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private UUID avatarFileId;
    private String phoneNumber;
    private boolean deleted;
    private boolean active;
    private List<UserRole> userRole;
    private UserActivityLog userActivityLog;

    public User(CreateUserCmd cmd) {
        this.userId = IdUtils.newUUID();
        this.username = cmd.getUsername();
        this.password = cmd.getPassword();
        this.email = cmd.getEmail();
        this.firstName = cmd.getFirstName();
        this.lastName = cmd.getLastName();
        this.dateOfBirth = cmd.getDateOfBirth();
        this.phoneNumber = cmd.getPhoneNumber();
        this.active = true;
        this.deleted = false;
        this.keycloakUserId = cmd.getKeycloakUserId();
        this.userRole = new ArrayList<>();

        if (cmd.getUserRole() != null) {
            cmd.getUserRole().forEach(createUserRolecmd -> {
                createUserRolecmd.setUserId(this.userId);
                userRole.add(new UserRole(createUserRolecmd));
            });
        }

    }

    public void update(UpdateUserCmd cmd) {
        if(cmd.getEmail() != null) {
            this.email = cmd.getEmail();
        }
        if(cmd.getFirstName() != null) {
            this.firstName = cmd.getFirstName();
        }
        if(cmd.getLastName() != null) {
            this.lastName = cmd.getLastName();
        }
        if(cmd.getPhoneNumber() != null) {
            this.phoneNumber = cmd.getPhoneNumber();
        }
        if(cmd.getDateOfBirth() != null) {
            this.dateOfBirth = cmd.getDateOfBirth();
        }
        if (cmd.getUserRole() != null && !cmd.getUserRole().isEmpty()) {
            if (this.userRole == null) {
                this.userRole = new ArrayList<>();
            }

            // Map existing roles by roleId
            Map<UUID, UserRole> existingRolesMap = new HashMap<>();
            for (UserRole ur : this.userRole) {
                existingRolesMap.put(ur.getRoleId(), ur);
            }

            // Update or add new roles
            for (CreateUserRoleCmd userRoleCmd : cmd.getUserRole()) {
                UUID roleId = userRoleCmd.getRoleId();
                userRoleCmd.setUserId(this.userId);
                if (!existingRolesMap.containsKey(roleId)) {
                    this.userRole.add(new UserRole(userRoleCmd));
                }
            }
        }

        UserActivityLog log = new UserActivityLog();
        log.setActivity("Update User");
        this.setUserActivityLog(log);
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;

    }

    public void changeAvatar(UUID fileId) {
        this.avatarFileId = fileId;
    }
}
