package org.example.entity.raw;

import lombok.Data;

import java.util.List;

@Data
public class Person {
    private String name;
    private Integer age;
    private List<Site> sites;
}
