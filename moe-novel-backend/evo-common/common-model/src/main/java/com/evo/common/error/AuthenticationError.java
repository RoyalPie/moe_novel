package com.evo.common.error;

public enum AuthenticationError implements ResponseError {
    UNKNOWN(40100001, "UNKNOWN"),
    UNAUTHORISED(40100002, "Unauthorised"),
    FORBIDDEN_ACCESS_TOKEN(40100003, "Access token has been forbidden due to user has logged out or deactivated"),
    FORBIDDEN_REFRESH_TOKEN(40100004, "Refresh token has been forbidden"),
    INVALID_REFRESH_TOKEN(40100005, "Refresh token has been forbidden"),
    VALIDATE_EXPIRATION_TIME(40100006, "validate expiration time"),
    VALIDATE_TOKEN_ID(40100007, "JWT Token is not an ID Token"),
    VALIDATE_ISSUER(40100008, "Issuer does not match idp"),
    ONLY_CLIENT_ACCESS_RESOURCE(40100009, "Only client can access this resource");

    private final Integer code;
    private final String message;

    private AuthenticationError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getName() {
        return this.name();
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return 401;
    }

    public Integer getCode() {
        return this.code;
    }
}