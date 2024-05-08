package org.example.entity.raw;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = {
        "description"
})
public class Site {
    private String id;
    private String url;
    private String description;
}
