package com.royal.iam_service.presentation.rest;

import com.evo.common.dto.TokenDTO;
import com.evo.common.dto.response.ApiResponses;
import com.nimbusds.jose.JOSEException;
import com.royal.iam_service.application.dto.request.CreateUserRequest;
import com.royal.iam_service.application.dto.request.LoginRequest;
import com.royal.iam_service.application.dto.request.VerifyOtpRequest;
import com.royal.iam_service.application.dto.response.UserDTO;
import com.royal.iam_service.application.service.AuthServiceCommand;
import com.royal.iam_service.application.service.AuthServiceQuery;
import com.royal.iam_service.application.service.ServiceStrategy;
import com.royal.iam_service.application.service.UserCommandService;
import com.royal.iam_service.application.service.impl.command.SelfIDPAuthServiceCommandImpl;
import com.royal.iam_service.domain.command.ResetKeycloakPasswordCmd;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/authenticate")
@RequiredArgsConstructor
public class AuthController {
    private String typeAuthCommandService = "keycloak_auth_command_service";
    private String typeAuthQueryService = "keycloak_auth_query_service";
    private final ServiceStrategy serviceStrategy;
    private AuthServiceCommand authServiceCommand;
    private AuthServiceQuery authServiceQuery;
    private final UserCommandService userCommandService;

    @Value("${keycloak.enabled}")
    private boolean isKeycloakEnabled;

    @PostConstruct
    public void init() {
        if (!isKeycloakEnabled) {
            this.typeAuthCommandService = "self_idp_auth_service";
            this.typeAuthQueryService = "self_idp_auth_query_service";
        }
        this.authServiceCommand = serviceStrategy.getAuthServiceCommand(typeAuthCommandService);
        this.authServiceQuery = serviceStrategy.getAuthServiceQuery(typeAuthQueryService);
    }

    @PostMapping("/login")
    public ApiResponses<TokenDTO> loginIam(@RequestBody LoginRequest loginRequest) {
        TokenDTO tokenDTO = authServiceCommand.authenticate(loginRequest);
        return ApiResponses.<TokenDTO>builder()
                .data(tokenDTO)
                .success(true)
                .code(201)
                .message("OTP sent to your Email")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/verify-otp")
    public ApiResponses<TokenDTO> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) throws ParseException, JOSEException {
        TokenDTO result = ((SelfIDPAuthServiceCommandImpl) authServiceCommand).verifyOTP(verifyOtpRequest);
        return ApiResponses.<TokenDTO>builder()
                .data(result)
                .success(true)
                .code(200)
                .message("OTP đã được xác nhận thành công")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/logout")
    ApiResponses<String> logoutIam(@Parameter(description = "request", hidden = true) HttpServletRequest request,
                                   @Parameter(description = "Refresh token từ client", required = true) @RequestParam String refreshToken
    ) {
        authServiceCommand.logoutIam(request, refreshToken);
        return ApiResponses.<String>builder()
                .data("Logout successful")
                .success(true)
                .code(200)
                .message("Logout successful")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/sign-up")
    public ApiResponses<UserDTO> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
        UserDTO userDTO = userCommandService.createDefaultUser(createUserRequest);
        return ApiResponses.<UserDTO>builder()
                .data(userDTO)
                .success(true)
                .code(201)
                .message("User created successfully")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/forgot-password")
    public ApiResponses<Void> requestPasswordReset(@Parameter(description = "Tên tài khoản của người dùng", required = true)
                                                   @RequestParam String username,
                                                   @RequestBody(required = false) ResetKeycloakPasswordCmd resetKeycloakPasswordCmd) {
        authServiceCommand.requestPasswordReset(username, resetKeycloakPasswordCmd);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message(isKeycloakEnabled ? "Password successfully reset" : "Reset password link sent to email")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PatchMapping("/reset-password")
    public ApiResponses<Void> resetPassword(@RequestParam String token, @RequestBody ResetKeycloakPasswordCmd resetKeycloakPasswordCmd) {
        authServiceCommand.resetPassword(token, resetKeycloakPasswordCmd);
        return ApiResponses.<Void>builder()
                .success(true)
                .code(200)
                .message("Password successfully reset")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }

    @PostMapping("/refresh")
    ApiResponses<TokenDTO> refresh(@Parameter(description = "Refresh token từ client", required = true)
                                   @RequestParam("refreshToken") String refreshToken) {
        TokenDTO tokenDTO = authServiceCommand.refresh(refreshToken);
        return ApiResponses.<TokenDTO>builder()
                .data(tokenDTO)
                .success(true)
                .code(200)
                .message("Refresh Token successful")
                .timestamp(System.currentTimeMillis())
                .status("OK")
                .build();
    }
}
