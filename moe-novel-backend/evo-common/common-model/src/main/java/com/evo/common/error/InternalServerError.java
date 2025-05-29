package com.evo.common.error;

public enum InternalServerError implements ResponseError {
    INTERNAL_SERVER_ERROR(50000001, "There are somethings wrong"),
    DATA_ACCESS_EXCEPTION(50000002, "Data access exception");

    private final Integer code;
    private final String message;

    private InternalServerError(Integer code, String message) {
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
        return 500;
    }

    public Integer getCode() {
        return this.code;
    }
}
