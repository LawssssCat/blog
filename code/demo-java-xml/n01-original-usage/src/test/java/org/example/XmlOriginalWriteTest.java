package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import utils.IOUtils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

public class XmlOriginalWriteTest {
    Document document;
    Transformer transformer;
    @BeforeEach
    void initEach() {
        // 格式
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        // 输出
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void test_write() throws IOException {
        // 内容
        Element posts = document.createElement("posts");
        {
            Element post = document.createElement("post");
            post.setTextContent("hello world!");
            post.setAttribute("id", "P001");
            posts.appendChild(post);
        }
        document.appendChild(posts);
        // 输出
        String outputTargetFilename = getClass().getName() + ".xml";
        File outputTargetFile = new File(Objects.requireNonNull(getClass().getResource("/")).getPath(), outputTargetFilename);
        try (OutputStream outputStream = Files.newOutputStream(outputTargetFile.toPath())) {
            try {
                transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8"); // 指定编码
                // transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // 美化输出
                transformer.transform(new DOMSource(document), new StreamResult(outputStream));
            } catch (TransformerException e) {
                throw new RuntimeException(e);
            }
        }
        Assertions.assertTrue(outputTargetFile.exists());
        // validate
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><posts><post id=\"P001\">hello world!</post></posts>",
                IOUtils.readFile(outputTargetFile));
    }
}
