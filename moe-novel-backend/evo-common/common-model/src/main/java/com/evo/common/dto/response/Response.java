package com.evo.common.dto.response;

import com.evo.common.enums.ErrorCodeClient;
import com.evo.common.error.ResponseError;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Response<T> implements Serializable {
    protected T data;
    private boolean success = true;
    private int code = 200;
    private String message;
    private long timestamp = Instant.now().toEpochMilli();
    private String status;
    @JsonIgnore
    private RuntimeException exception;

    public Response() {
        this.status = ErrorCodeClient.SUCCESS.name();
    }

    public static <T> Response<T> of(T res) {
        Response<T> response = new Response<T>();
        response.data = res;
        response.success();
        return response;
    }

    public static <T> Response<T> ok() {
        Response<T> response = new Response<T>();
        response.success();
        return response;
    }

    public static <T> Response<T> fail(RuntimeException exception) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setStatus(ErrorCodeClient.FAIL.name());
        response.setException(exception);
        return response;
    }

    public static <T> Response<T> fail(String message, RuntimeException exception) {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setStatus(ErrorCodeClient.FAIL.name());
        response.setException(exception);
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> fail() {
        Response<T> response = new Response<T>();
        response.setSuccess(false);
        response.setStatus(ErrorCodeClient.FAIL.name());
        return response;
    }

    public Response<T> success() {
        this.success = true;
        this.code = 200;
        this.status = ErrorCodeClient.SUCCESS.name();
        return this;
    }

    public Response<T> data(T res) {
        this.data = res;
        return this;
    }

    public Response<T> success(String message) {
        this.success = true;
        this.message = message;
        this.code = 200;
        this.status = ErrorCodeClient.SUCCESS.name();
        return this;
    }

    public Response<T> fail(String message, ResponseError responseError) {
        this.success = false;
        this.code = responseError.getCode();
        this.status = ErrorCodeClient.FAIL.name();
        if (StringUtils.hasText(message)) {
            this.message = message;
        } else {
            this.message = responseError.getMessage();
        }

        return this;
    }

    public Response<T> fail(Exception ex, ResponseError responseError) {
        this.success = false;
        this.code = responseError.getCode();
        this.status = ErrorCodeClient.FAIL.name();
        this.message = ex.getMessage();
        return this;
    }

}
