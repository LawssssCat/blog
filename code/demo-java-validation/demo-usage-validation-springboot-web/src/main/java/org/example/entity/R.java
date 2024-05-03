package org.example.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class R {
    private String state;
    private Object data;
    public static R SUCCESS(Object data) {
        return new R("200", data);
    }

    public static R ERROR(Object data) {
        return new R("400", data);
    }
}
