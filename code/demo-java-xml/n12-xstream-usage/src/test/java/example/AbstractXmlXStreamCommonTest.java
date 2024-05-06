package example;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
public abstract class AbstractXmlXStreamCommonTest {
    File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), getClass().getName() + ".xml");

    /**
     * 实体类 to xml
     */
    void testObj2Xml(XStream xStream, Object o, Writer writer) {
        String xml = xStream.toXML(o);
        log.info(xml);
        xStream.toXML(o, writer);
        log.info("success output {}", file.getAbsolutePath());
        Assertions.assertTrue(file.exists());
    }

    void testObj2Xml(XStream xStream, Object o) {
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            testObj2Xml(xStream, o, fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
