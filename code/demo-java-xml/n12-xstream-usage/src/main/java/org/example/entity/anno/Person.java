package org.example.entity.anno;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import lombok.Data;

import java.util.List;

@Data
@XStreamAlias("person") // 自定义标签，而非默认的 “包名.类名”
public class Person {
    @XStreamAlias("姓名") // 自定义标签
    private String name;
    @XStreamAsAttribute // 生成属性，而非子标签
    private Integer age;
//    @XStreamImplicit(itemFieldName = "site") // 去掉集合标签 <sites>
    private List<Site> sites;
}
