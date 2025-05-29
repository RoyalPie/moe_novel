package com.evo.common.error;

public enum AuthorizationError implements ResponseError {
    ACCESS_DENIED(40300001, "Access Denied"),
    NOT_SUPPORTED_AUTHENTICATION(40300002, "Your authentication has not been supported yet");

    private final Integer code;
    private final String message;

    private AuthorizationError(Integer code, String message) {
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
        return 403;
    }

    public Integer getCode() {
        return this.code;
    }
}
