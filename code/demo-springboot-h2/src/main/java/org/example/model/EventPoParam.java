package org.example.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventPoParam {
    private Integer id;
    private String param;
}
