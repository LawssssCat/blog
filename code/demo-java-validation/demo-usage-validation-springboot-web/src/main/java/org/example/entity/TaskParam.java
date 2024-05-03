package org.example.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TaskParam {
    @NotBlank
    private String id;
}
