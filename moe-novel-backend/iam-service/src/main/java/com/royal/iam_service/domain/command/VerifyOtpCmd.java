package com.royal.iam_service.domain.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpCmd {
    private String username;
    private String otp;
}
