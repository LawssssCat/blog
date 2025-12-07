package com.example.toolserverhttpcounter.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private int code;
    private T msg;

    public Result(int code, T msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success(T msg) {
        return new Result<>(200, msg);
    }
}
