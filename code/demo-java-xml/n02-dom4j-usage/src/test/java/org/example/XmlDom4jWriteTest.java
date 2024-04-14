package org.example;

import org.dom4j.*;
import org.dom4j.dom.DOMAttribute;
import org.dom4j.io.XMLWriter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class XmlDom4jWriteTest {
    @Test
    void test() throws IOException {
        Document document = DocumentHelper.createDocument();
        Element content = document.addElement("content");
        Element owner = content.addElement("owner");
        owner.setText("steven");
        Element posts = content.addElement("posts");
        for (int i = 0; i < 2; i++) {
            Element post = posts.addElement("post");
            List<Attribute> attributes = Collections.singletonList(new DOMAttribute(QName.get("id"), "P00" + i));
            post.setAttributes(attributes);
        }
        // write
        File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), getClass().getName() + ".xml");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            XMLWriter xmlWriter = new XMLWriter(fileOutputStream);
            xmlWriter.write(content);
        }
        // validate
        Assertions.assertTrue(file.exists());
        Assertions.assertEquals("<content><owner>steven</owner><posts><post id=\"P000\"/><post id=\"P001\"/></posts></content>",
                IOUtils.readFile(file));
    }
}
