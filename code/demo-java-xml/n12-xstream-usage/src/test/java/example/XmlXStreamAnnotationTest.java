package example;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

@Slf4j
public class XmlXStreamAnnotationTest extends AbstractXmlXStreamCommonTest {
    static XStream xStream;

    @BeforeAll
    static void beforeAll() {
        xStream = new XStream(); // 线程安全
        xStream.autodetectAnnotations(true);
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
        super.testXml2Obj(xStream, Person.class, new Class[] {Person.class, Site.class},  person);
    }
}
