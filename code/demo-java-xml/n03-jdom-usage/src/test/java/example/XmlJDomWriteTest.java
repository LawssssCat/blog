package example;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class XmlJDomWriteTest {
    private Element addContent(Element p, Element c) {
        p.addContent(c);
        return c;
    }
    @Test
    void test() throws IOException {
        Element content = new Element("content");
        Element owner = addContent(content, new Element("owner"));
        owner.setText("steven");
        Element posts = addContent(content, new Element("posts"));
        for (int i = 0; i < 2; i++) {
            Element post = addContent(posts, new Element("post"));
            List<Attribute> attributes = Collections.singletonList(new Attribute("id", "P00" + i));
            post.setAttributes(attributes);
        }
        // write
        File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), getClass().getName() + ".xml");
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            XMLOutputter xmlWriter = new XMLOutputter();
            Format format = Format.getCompactFormat();
            format.setEncoding(StandardCharsets.UTF_8.name());
            xmlWriter.setFormat(format);
            xmlWriter.output(content, fileOutputStream);
        }
        // validate
        Assertions.assertTrue(file.exists());
        Assertions.assertEquals("<content><owner>steven</owner><posts><post id=\"P000\" /><post id=\"P001\" /></posts></content>",
                IOUtils.readFile(file));
    }
}
