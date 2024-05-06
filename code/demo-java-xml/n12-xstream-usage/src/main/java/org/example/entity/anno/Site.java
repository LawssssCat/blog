package org.example.entity.anno;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;

@XStreamAlias("site")
@Data
public class Site {
    private String id;
    private String url;
}
