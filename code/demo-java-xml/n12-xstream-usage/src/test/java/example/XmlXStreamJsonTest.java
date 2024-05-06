package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

public class XmlXStreamJsonTest extends AbstractXmlXStreamCommonTest{
    {
        file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), getClass().getName() + ".json");
    }

    static XStream xStream;

    @BeforeAll
    static void beforeAll() {
        // 可以选择不同的数据转换器，如 DomDriver、StaxDriver、JDomDriver 等，用于控制 XML 数据的读写方式。
        HierarchicalStreamDriver driver = new JettisonMappedXmlDriver(); // 引入 JSON 转换驱动
        xStream = new XStream(driver);
        // 可以通过 setMode() 方法设置不同的模式，如 NO_REFERENCES、ID_REFERENCES、HIERARCHICAL、SINGLE_NODE 等，用于处理对象之间的引用关系。
        // xStream.setMode(XStream.NO_REFERENCES);
        xStream.autodetectAnnotations(true);
    }

    /**
     * 实体类 to xml
     */
    @Test
    @Order(1)
    void testObj2Xml() throws IOException {
        Person person = XmlXStreamAnnotationTest.newPerson();
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            testObj2Xml(xStream, person, fileOutputStream);
        }
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
