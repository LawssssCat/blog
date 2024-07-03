package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    public static <T> Result successOf(T data) {
        return new Result(200, "success", data);
    }
    public static <T> Result errorOf(Integer code, String msg, T data) {
        return new Result(code, msg, data);
    }
}
