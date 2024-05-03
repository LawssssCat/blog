package org.example.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserVo {
    private String id;
    private String username;
}
