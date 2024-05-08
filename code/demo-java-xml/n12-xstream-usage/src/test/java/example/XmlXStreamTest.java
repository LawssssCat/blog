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
        // 自定义标签名 —— "类/属性混叠"
        xStream.alias("person", Person.class); // 否则 <org.example.Person> 而不是 <person>
        xStream.aliasField("姓名", Person.class, "name"); // <name> to <姓名> in Person.class
        // 自定义包前缀 ——+ "包混叠"
        xStream.aliasPackage("my", "org.example.entity.raw"); // org.example.entity.raw.Site -> my.Site
        // 生成属性，而非子标签
        xStream.useAttributeFor(Person.class, "age"); // 属性 <person age="18">
        // 去掉集合标签 —— "隐式集合混叠"
        xStream.addImplicitCollection(Person.class, "sites"); // 去掉 <sites>
        // 忽略字段
        xStream.omitField(Site.class, "description");
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
            site.setDescription("description1");
            person.getSites().add(site);
        }
        {
            Site site = new Site();
            site.setId("222");
            site.setUrl("https://n2.example.org");
            site.setDescription("description2");
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
