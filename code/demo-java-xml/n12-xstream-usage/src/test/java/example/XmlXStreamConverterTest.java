package example;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.example.converter.SiteConverter;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

@Slf4j
public class XmlXStreamConverterTest extends AbstractXmlXStreamCommonTest {
    static XStream xStream;

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
        super.testObj2Xml(xStream, person);
    }

    /**
     * xml to 实体类
     */
    @Test
    @Order(2)
    void testXml2Obj() {
        Person person = XmlXStreamAnnotationTest.newPerson();
        super.testXml2Obj(xStream, Person.class, new Class[] {Person.class, Site.class},  person);
    }
}
