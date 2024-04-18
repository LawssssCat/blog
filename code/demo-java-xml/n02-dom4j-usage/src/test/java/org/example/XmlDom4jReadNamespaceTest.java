package org.example;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class XmlDom4jReadNamespaceTest {
    Document read;
    @BeforeEach
    void beforeEach() {
        SAXReader saxReader = new SAXReader();
        try {
            read = saxReader.read(getClass().getResource("/message002.xml"));
        } catch (DocumentException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void test_getName_and_getQualifiedName() {
        Element rootElement = read.getRootElement();
        Assertions.assertEquals("my-content", rootElement.getName());
        Assertions.assertEquals("xxx:my-content", rootElement.getQualifiedName());
        Assertions.assertEquals("xxx", rootElement.getNamespacePrefix());
        Assertions.assertEquals("http://example.org/xxx", rootElement.getNamespaceURI());
        Assertions.assertNull(rootElement.element("xxx:web-site"));
        Element webSite = rootElement.element("web-site");
        Assertions.assertNotNull(webSite);
        Assertions.assertEquals("web-site", webSite.getName());
        Assertions.assertEquals("xxx:web-site", webSite.getQualifiedName());
        Assertions.assertEquals("xxx", webSite.getNamespacePrefix());
        Assertions.assertEquals("http://example.org/xxx", webSite.getNamespaceURI());
    }
    @Test
    void test_XPath() {
        Element rootElement = read.getRootElement();
        Assertions.assertNull(rootElement.selectSingleNode("web-site"));
        Assertions.assertNotNull(rootElement.selectSingleNode("xxx:web-site"));
    }
}
