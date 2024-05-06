package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
public abstract class AbstractXmlXStreamCommonTest {
    final File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), getClass().getName() + ".xml");

    /**
     * 实体类 to xml
     */
    void testObj2Xml(XStream xStream, Object o) {
        // toXml
        String xml = xStream.toXML(o);
        log.info(xml);
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            xStream.marshal(o, new CompactWriter(fileOutputStream));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        log.info("success output {}", file.getAbsolutePath());
        Assertions.assertTrue(file.exists());
    }

    /**
     * xml to 实体类
     */
    void testXml2Obj(XStream xStream, Class<?> clazz, Class<?>[] allowTypes, Object expect) {
        Assertions.assertTrue(file.exists());
        // to obj
        xStream.allowTypes(allowTypes);
        Object o = xStream.fromXML(file);
        log.info("success to obj {}", o);
        Assertions.assertInstanceOf(clazz, o);
        Assertions.assertEquals(expect, o);
    }
}
