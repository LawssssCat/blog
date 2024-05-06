package example;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.raw.Person;
import org.example.entity.raw.Site;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
public class XmlXStreamTest extends AbstractXmlXStreamCommonTest {
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
        super.testObj2Xml(xStream, person);
    }

    /**
     * xml to 实体类
     */
    @Test
    @Order(2)
    void testXml2Obj() {
        Person person = newPerson();
        super.testXml2Obj(xStream, Person.class, new Class[] {Person.class, Site.class}, person);
    }
}
