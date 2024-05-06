package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.CompactWriter;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.raw.Person;
import org.example.entity.raw.Site;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

@Slf4j
public class XmlXStreamTest {
    final static File FILE = new File(Objects.requireNonNull(XmlXStreamTest.class.getResource("/")).getFile(),
            XmlXStreamTest.class.getName() + ".xml");

    static XStream xStream; // 线程安全
    @BeforeAll
    static void beforeAll() {
        xStream = new XStream();
        xStream.alias("person", Person.class); // 否则 <org.example.Person> 而不是 <person>
        xStream.aliasField("姓名", Person.class, "name"); // <name> to <姓名> in Person.class
        xStream.useAttributeFor(Person.class, "age"); // 属性 <person age="18">
        xStream.alias("site", Site.class); // 否则 org.example.Site
//        xStream.addImplicitCollection(Person.class, "sites"); // 去掉 <sites>
    }

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
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(Files.newOutputStream(FILE.toPath()), StandardCharsets.UTF_8)) {
            xStream.marshal(person, new CompactWriter(fileOutputStream));
            // xStream.toXML(person, fileOutputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(FILE.exists());
        log.info("success output {}", FILE.getAbsolutePath());
    }

    /**
     * xml to 实体类
     */
    @Test
    @Order(2)
    void testXml2Obj() {
        xStream.allowTypes(new Class[] {Person.class, Site.class});
        Object o = xStream.fromXML(FILE);
        System.out.println("o = " + o);
        Assertions.assertInstanceOf(Person.class, o);
        Person p = (Person) o;
        Assertions.assertEquals("steven", p.getName());
        Assertions.assertEquals(18, p.getAge());
    }
}
