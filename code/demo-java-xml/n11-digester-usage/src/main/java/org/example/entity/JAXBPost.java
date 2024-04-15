package org.example.entity;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
@Data
public class JAXBPost {
    @XmlAttribute(name = "id")
    private String id;
    private String title;
    private String content;
}
