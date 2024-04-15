package org.example.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "my-content")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@Data
public class JAXBMyContent {
    @XmlElement(name = "web-site")
    private String webSite;
    private String owner;
    private String description;
    @XmlElementWrapper(name = "posts")
    @XmlElement(name = "post", type = JAXBPost.class)
    private List<JAXBPost> posts;
}
