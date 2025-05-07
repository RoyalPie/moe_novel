package com.royal.iam_service.domain.command;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCmd {
    private UUID keycloakUserId;
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private UUID avatarId;
    private String phoneNumber;
    private boolean active;
    private List<CreateUserRoleCmd> userRole;
}
