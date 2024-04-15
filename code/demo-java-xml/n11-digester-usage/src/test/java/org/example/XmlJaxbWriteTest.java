package org.example;

import org.example.entity.JAXBMyContent;
import org.example.entity.JAXBPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.IOUtils;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class XmlJaxbWriteTest {
    public static File FILE = new File(
            Objects.requireNonNull(XmlJaxbWriteTest.class.getResource("/")).getFile(),
            XmlJaxbWriteTest.class.getName() + ".xml"
    );
    @Test
    void test() throws IOException {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JAXBMyContent.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            // marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // 格式化输出
            marshaller.setProperty(Marshaller.JAXB_ENCODING, StandardCharsets.UTF_8.name());
            marshaller.marshal(buildMyContent(), FILE);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(FILE.exists());
        String xmlContent = IOUtils.readFile(FILE);
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                        "<my-content>" +
                        "<description>build by jaxp</description><owner>steven</owner>" +
                        "<posts>" +
                            "<post id=\"P000\">" +
                                "<content>JAXP-POST-CONTENT</content>" +
                                "<title>JAXP-TITLE-0</title>" +
                            "</post>" +
                            "<post id=\"P001\">" +
                                "<content>JAXP-POST-CONTENT</content>" +
                                "<title>JAXP-TITLE-1</title>" +
                            "</post>" +
                        "</posts>" +
                        "<web-site>https://org.example</web-site>" +
                        "</my-content>",
                xmlContent);
        Assertions.assertTrue(xmlContent.contains("my-content"));
        Assertions.assertTrue(xmlContent.contains("web-site"));
        Assertions.assertTrue(xmlContent.contains("<post id=\"P000\">"));
    }

    private JAXBMyContent buildMyContent() {
        // 报错1： [com.sun.istack.internal.SAXException2: 由于类型 "org.example.entity.MyContent" 缺少 @XmlRootElement 注释, 无法将该类型编集为元素]
        // 解决1： 需要 JAXPMyContent 类上添加 @XmlRootElement 注解
        // 报错2： [com.sun.istack.internal.SAXException2: 由于类型 "org.example.entity.JAXPMyContent" 对此上下文是未知的, 无法将该类型编集为元素。]
        // 解决2： 需要 MyContent 类上加 @XmlSeeAlso({JAXPMyContent.class}) 注解
        // MyContent myContent = new MyContent();
        JAXBMyContent myContent = new JAXBMyContent();
        myContent.setWebSite("https://org.example");
        myContent.setOwner("steven");
        myContent.setDescription("build by jaxp");
        List<JAXBPost> posts = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            JAXBPost post = new JAXBPost();
            post.setId("P00" + i);
            post.setTitle("JAXP-TITLE-"+i);
            post.setContent("JAXP-POST-CONTENT");
            posts.add(post);
        }
        myContent.setPosts(posts);
        return myContent;
    }
}
