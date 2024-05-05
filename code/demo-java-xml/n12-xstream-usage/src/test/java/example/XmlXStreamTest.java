package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import lombok.extern.slf4j.Slf4j;
import org.example.Person;
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
public class XmlXStreamTest {
    final static File FILE = new File(Objects.requireNonNull(XmlXStreamTest.class.getResource("/")).getFile(),
            XmlXStreamTest.class.getName() + ".xml");

    static XStream xStream;
    @BeforeAll
    static void beforeAll() {
        xStream = new XStream();
        xStream.alias("person", Person.class); // 否则 <org.example.Person> 而不是 <person>
        xStream.aliasField("姓名", Person.class, "name"); // <姓名>
        xStream.useAttributeFor(Person.class, "age"); // 属性 <person age="18">
    }

    @Test
    @Order(1)
    void testObj2Xml() {
        Person person = new Person();
        person.setName("steven");
        person.setAge(18);
        // toXml
        String xml = xStream.toXML(person);
        log.info(xml);
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(Files.newOutputStream(FILE.toPath()), StandardCharsets.UTF_8)) {
            xStream.marshal(person, new CompactWriter(fileOutputStream));
            // xStream.toXML(person, fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(FILE.exists());
        log.info("success output {}", FILE.getAbsolutePath());
    }

    @Test
    @Order(2)
    void testXml2Obj() {
        xStream.allowTypes(new Class[] {Person.class});
        Object o = xStream.fromXML(FILE);
        System.out.println("o = " + o);
        Assertions.assertInstanceOf(Person.class, o);
        Person p = (Person) o;
        Assertions.assertEquals("steven", p.getName());
        Assertions.assertEquals(18, p.getAge());
    }
}
