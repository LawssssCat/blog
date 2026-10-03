package org.example.commons.model;

import lombok.*;
import org.example.commons.constant.RestCode;

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
