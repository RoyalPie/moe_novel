package com.royal.novel_service.infrastructure.support.exceptions;

import com.evo.common.error.ResponseError;

public enum BadRequestError implements ResponseError {
    NOVEL_EXITS(40015001, "Novel exits!"),
    NOVEL_NOT_FOUND(40015002, "Novel not found"),
    GENRE_EXITS(40016001, "Genre exits!"),
    GENRE_NOT_FOUND(40016002, "Genre not found"),
    TAG_EXITS(40016003, "Tag exits!"),
    TAG_NOT_FOUND(40016004, "Tag not found"),
    ;

    private final Integer code;
    private final String message;

    BadRequestError(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public int getStatus() {
        return 400;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }
}
