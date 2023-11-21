package com.dragonsky.youcomego.global.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@Builder
public class ResultDto<T> {
    private HttpStatus statusCode;
    private String message;
    private T data;

    public ResultDto(final HttpStatus statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = null;
    }

    public static<T> ResultDto<T> res(final HttpStatus statusCode, final String message) {
        return res(statusCode, message, null);
    }

    public static<T> ResultDto<T> res(final HttpStatus statusCode, final String message, final T t) {
        return ResultDto.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .message(message)
                .build();
    }
}