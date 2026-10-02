package org.example.model;

import lombok.*;
import org.example.constant.RestCode;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestResult<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> RestResult<T> ok(String msg, T data) {
        return new RestResult<>(RestCode.OK, msg, data);
    }

    public static <T> RestResult<T> ok(T data) {
        return ok(null, data);
    }
}
