package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import lombok.extern.slf4j.Slf4j;
import org.example.converter.SiteConverter;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

@Slf4j
public class XmlXStreamConverterTest {
    static XStream xStream;

    File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(),
            getClass().getName() + ".xml");

    @BeforeAll
    static void beforeAll() {
        xStream = new XStream();
        xStream.autodetectAnnotations(true);
        xStream.registerConverter(new SiteConverter());
    }

    /**
     * 实体类 to xml
     */
    @Test
    @Order(1)
    void testObj2Xml() {
        Person person = XmlXStreamAnnotationTest.newPerson();
        // toXml
        String xml = xStream.toXML(person);
        log.info(xml);
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            xStream.marshal(person, new CompactWriter(fileOutputStream));
            // xStream.toXML(person, fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(file.exists());
        log.info("success output {}", file.getAbsolutePath());
    }

    /**
     * xml to 实体类
     */
    @Test
    @Order(2)
    void testXml2Obj() {
        Assertions.assertTrue(file.exists());
        try {
            for (String readAllLine : Files.readAllLines(file.toPath())) {
                log.info(readAllLine);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        xStream.allowTypes(new Class[] {Person.class, Site.class});
        Object o = xStream.fromXML(file);
        log.info("xml to obj: {}", o);
        Assertions.assertInstanceOf(Person.class, o);
        Person p = (Person) o;
        Assertions.assertEquals("steven", p.getName());
        Assertions.assertEquals(18, p.getAge());
    }
}
