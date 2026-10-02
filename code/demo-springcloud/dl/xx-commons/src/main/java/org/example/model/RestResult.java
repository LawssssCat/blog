package org.example.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.constant.RestCode;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RestResult<T> {
    @Getter
    private int code;

    @Getter
    private String msg;

    @Getter
    private T data;

    public static <T> RestResult<T> ok(String msg, T data) {
        return new RestResult<>(RestCode.OK, msg, data);
    }

    public static <T> RestResult<T> ok(T data) {
        return ok(null, data);
    }
}
