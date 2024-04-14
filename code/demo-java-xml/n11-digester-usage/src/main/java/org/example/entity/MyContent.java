package org.example.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MyContent {
    private String webSite;
    private String owner;
    private String description;
    private List<Post> posts;
}
