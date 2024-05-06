package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
public class XmlXStreamAnnotationTest {
    static XStream xStream;

    static {
        xStream = new XStream(); // 线程安全
        xStream.autodetectAnnotations(true);
    }

    File file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(),
            getClass().getName() + ".xml");

    public static Person newPerson() {
        Person person = new Person();
        person.setName("steven");
        person.setAge(18);
        person.setSites(new ArrayList<Site>());
        {
            Site site = new Site();
            site.setId("111");
            site.setUrl("http://n1.example.org");
            person.getSites().add(site);
        }
        {
            Site site = new Site();
            site.setId("222");
            site.setUrl("https://n2.example.org");
            person.getSites().add(site);
        }
        return person;
    }

    /**
     * 实体类 to xml
     */
    @Test
    @Order(1)
    void testObj2Xml() {
        Person person = newPerson();
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
        xStream.allowTypes(new Class[] {Person.class, Site.class});
        Object o = xStream.fromXML(file);
        System.out.println("o = " + o);
        Assertions.assertInstanceOf(Person.class, o);
        Person p = (Person) o;
        Assertions.assertEquals("steven", p.getName());
        Assertions.assertEquals(18, p.getAge());
    }
}
