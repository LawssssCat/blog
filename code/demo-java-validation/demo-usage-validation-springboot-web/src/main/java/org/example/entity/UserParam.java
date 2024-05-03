package org.example.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserParam {
    public static interface Insert { }
    @NotBlank
    private String id;
    @NotBlank(groups = {
        Insert.class
    })
    private String username;
}
