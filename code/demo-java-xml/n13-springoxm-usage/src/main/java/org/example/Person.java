package org.example;

import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@XmlRootElement
public class Person {
    private String id;
    private String name;
    private Integer age;
}
