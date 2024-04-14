package org.example;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class XmlDom4jReadTest {
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
    void test_getRootElement() {
        Element rootElement = read.getRootElement();
        Assertions.assertEquals("my-content", rootElement.getQualifiedName());
        Assertions.assertEquals(2, rootElement.element("posts").elements().size());
        Assertions.assertArrayEquals(new String[] {"P001", "P002"}, rootElement.element("posts").elements().stream().map(e -> e.attribute("id").getValue()).toArray());
    }
    @Test
    void test_nodeIterator() {
        int countContentElement = 0;
        int countPostElement = 0;
        List<String> contentElementQNameList = new ArrayList<>();
        List<String> postElementIdList = new ArrayList<>();
        // test
        Element rootElement = read.getRootElement();
        Iterator<Element> contentIterator = rootElement.elementIterator();
        while (contentIterator.hasNext()) {
            countContentElement++;
            Element contentElement = contentIterator.next();
            System.out.println("contentElement = " + contentElement);
            contentElementQNameList.add(contentElement.getQualifiedName());
            if("posts".equals(contentElement.getQualifiedName())) {
                Iterator<Element> postIterator = contentElement.elementIterator();
                while (postIterator.hasNext()) {
                    countPostElement++;
                    Element post = postIterator.next();
                    System.out.println("postElement = " + post);
                    postElementIdList.add(post.attributeValue("id"));
                }
            }
        }
        Assertions.assertEquals(4, countContentElement);
        Assertions.assertEquals(2, countPostElement);
        Assertions.assertArrayEquals(new String[] {"web-site", "owner", "description", "posts"}, contentElementQNameList.toArray());
        Assertions.assertArrayEquals(new String[] {"P001", "P002"}, postElementIdList.toArray());
    }
}
