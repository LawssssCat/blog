package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class XmlOriginalReadTest {
    static Document document;

    @BeforeAll
    static void initClass() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            InputStream resourceAsStream = XmlOriginalReadTest.class.getResourceAsStream("/message001.xml");
            try {
                document = documentBuilder.parse(resourceAsStream);
            } catch (SAXException | IOException e) {
                throw new RuntimeException(e);
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 测试 dom 基本使用
     */
    @Test
    void test_getElementsByTagName_and_getChildNodes() {
        NodeList posts = document.getElementsByTagName("post");
        Assertions.assertEquals(2, posts.getLength());
        for (int i = 0; i < posts.getLength(); i++) {
            // Element 专指 “标签” 元素
            Element post = (Element) posts.item(i);
            // 👆 Node 实现有： Element Attr Comment Text ...
            log.info("{} class={}", i, post.getClass());
            log.info("{} nodeName={}", i, post.getNodeName());
            log.info("{} attrId={}", i, post.getAttribute("id"));
            NodeList childNodes = post.getChildNodes();
            Assertions.assertEquals(5, childNodes.getLength());
            // 👆 有 5 个子 Node，因为除了 2 个 Element 外，它们中间的空间会被识别成 Text
            // Text Comment（忽略） Text（如入上一个text） Element Text Element Text
            for (int j = 0; j < childNodes.getLength(); j++) {
                Node postDefinition = childNodes.item(j);
                log.info("{}-{} class={}", i, j, postDefinition.getClass());
                if (postDefinition instanceof Element) {
                    log.info("{}-{} nodeName={}", i, j, postDefinition.getNodeName());
                    log.info("{}-{} text={}", i, j, postDefinition.getTextContent());
                    // 💡 获取文本内容，也可以 postDefinition.getFirstChild().getNodeValue()
                }
            }
        }
    }

    /**
     * 测试 xpath 用法
     * 封装： jaxen.jaxen:1.2.0
     */
    @Test
    void test_xpath() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xPath = xPathFactory.newXPath();
        try {
            // xpath 语法
            XPathExpression xPathExpression = xPath.compile("//posts//post[@id='P002']//title//text()");
            Node node = (Node) xPathExpression.evaluate(document, XPathConstants.NODE);
            Assertions.assertEquals("文章标题02", node.getNodeValue());
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
