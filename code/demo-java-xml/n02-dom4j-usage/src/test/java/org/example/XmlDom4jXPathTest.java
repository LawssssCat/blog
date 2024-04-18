package org.example;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

@Slf4j
public class XmlDom4jXPathTest {
    Document read;
    @BeforeEach
    void beforeEach() {
        SAXReader saxReader = new SAXReader();
        try {
            read = saxReader.read(getClass().getResource("/message001.xml"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void test_selectNodes() {
        List<Node> nodes = read.selectNodes("//posts/*");
        log.info("{}", nodes);
        Assertions.assertEquals(2, nodes.size());
        nodes.forEach(n -> {
            Assertions.assertInstanceOf(Element.class, n);
            Assertions.assertEquals("post", n.getName());
        });
    }
    @Test
    void test_selectSingleNode() {
        Node node = read.selectSingleNode("//posts/*[2]");
        Assertions.assertInstanceOf(Element.class, node);
        Assertions.assertEquals("post", node.getName());
        Element e = (Element) node;
        Assertions.assertEquals("P002", e.attribute("id").getValue());
        Assertions.assertEquals(2, e.elements().size());
    }
    @Test
    void test_Predicates() {
        Element node = (Element) read.selectSingleNode("//posts/*[@id='P002']");
        log.info("{}", node);
        Assertions.assertEquals("post", node.getQualifiedName());
        Assertions.assertEquals("文章标题02", node.element("title").getStringValue());
        Assertions.assertEquals("hello world 02！", node.element("content").getStringValue());
    }
}
