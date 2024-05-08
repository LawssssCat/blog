package example;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.persistence.FilePersistenceStrategy;
import com.thoughtworks.xstream.persistence.XmlArrayList;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * 持久化对象内容
 */
public class XmlXStreamObjectPersistentTest extends AbstractXmlXStreamCommonTest {
    {
        // 需要为一个目录
        file = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(), getClass().getName());
        file.mkdirs();
        Assertions.assertTrue(file.exists());
        Assertions.assertTrue(file.isDirectory());
    }

    static XStream xStream;

    static List<Object> temp = new ArrayList<>();

    @BeforeAll
    static void beforeAll() {
        xStream = new XStream(); // 线程安全
        xStream.autodetectAnnotations(true);
        xStream.allowTypes(new Class[] {Person.class, Site.class});
    }

    static FilePersistenceStrategy filePersistenceStrategy;


    @BeforeEach
    void beforeEach() {
        filePersistenceStrategy = new FilePersistenceStrategy(file, xStream);
    }

    /**
     * 持久化1
     */
    @Test
    @Order(1)
    void testFilePersistenceStrategySave1() {
        List xmlArrayList = new XmlArrayList(filePersistenceStrategy);
        List<Object> objects = XmlXStreamObjectIOTest.newListObject();
        xmlArrayList.addAll(objects);
        temp.addAll(objects);
    }

    /**
     * 持久化1
     */
    @Test
    @Order(2)
    void testFilePersistenceStrategySave2() {
        List xmlArrayList = new XmlArrayList(filePersistenceStrategy);
        Person person = XmlXStreamAnnotationTest.newPerson();
        xmlArrayList.add(person);
        temp.add(person);
    }

    @Test
    @Order(11)
    void testFilePersistenceStrategyLoad1() {
        List xmlArrayList = new XmlArrayList(filePersistenceStrategy);
        int count = xmlArrayList.size();
        Iterator iterator = xmlArrayList.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        Assertions.assertEquals(temp.size(), count);
    }

    @AfterAll
    static void afterAll() {
        // 清空，初始化环境
        if (temp.isEmpty()) {
            List xmlArrayList = new XmlArrayList(filePersistenceStrategy);
            Iterator iterator = xmlArrayList.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                iterator.remove();
            }
        }
    }
}
