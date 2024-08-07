package org.example.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class JsonCreatorUser {
    private String name;
    private Integer age;

    @JsonCreator
    public JsonCreatorUser(@JsonProperty("name") String name, @JsonProperty("age") Integer age) {
        this.name = name;
        this.age = age;
    }
}
