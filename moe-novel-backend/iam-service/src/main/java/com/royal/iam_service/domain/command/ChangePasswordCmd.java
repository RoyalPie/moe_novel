package com.royal.iam_service.domain.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordCmd {
    private String oldPassword;
    private String newPassword;
}
