package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.security.NoTypePermission;
import lombok.extern.slf4j.Slf4j;
import org.example.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
        try (FileOutputStream fileOutputStream = new FileOutputStream(FILE)) {
            xStream.toXML(person, fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(FILE.exists());
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
