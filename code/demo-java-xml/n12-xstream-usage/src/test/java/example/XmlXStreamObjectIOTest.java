package example;

import com.thoughtworks.xstream.XStream;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.anno.Person;
import org.example.entity.anno.Site;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * XStream 流式输出
 */
@Slf4j
public class XmlXStreamObjectIOTest extends AbstractXmlXStreamCommonTest {
    static XStream xStream;

    @BeforeAll
    static void beforeAll() {
        xStream = new XStream(); // 线程安全
        xStream.autodetectAnnotations(true);
        xStream.allowTypes(new Class[] {Person.class, Site.class});
    }

    public static List<Object> newListObject() {
        List<Object> expects = new ArrayList<>();
        expects.add(XmlXStreamAnnotationTest.newPerson());
        expects.add(XmlXStreamAnnotationTest.newPerson());
        expects.add("hello world!");
        expects.add(1024);
        return expects;
    }

    /**
     * 持续写出对象
     */
    @Test
    @Order(1)
    void testObjectOutputStream() throws IOException {
        try (FileOutputStream fileInputStream = new FileOutputStream(file)) {
            ObjectOutputStream objectOutputStream = xStream.createObjectOutputStream(fileInputStream);
            for (Object e : newListObject()) {
                if (e instanceof Integer) {
                    objectOutputStream.writeInt((Integer) e);
                } else if (e instanceof String) {
                    objectOutputStream.writeUTF((String) e);
                } else {
                    objectOutputStream.writeObject(e);
                }
            }
            objectOutputStream.close();
        }
        Assertions.assertTrue(file.exists());
        Files.readAllLines(file.toPath()).forEach(log::info);
    }

    /**
     * 持续读入对象
     */
    @Test
    @Order(2)
    void testObjectInputStream() throws IOException, ClassNotFoundException {
        List<Object> real = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            ObjectInputStream objectInputStream = xStream.createObjectInputStream(fileInputStream);
            for (Object e : newListObject()) {
                Object r = null;
                if (e instanceof Integer) {
                    r = objectInputStream.readInt();
                } else if (e instanceof String) {
                    r = objectInputStream.readUTF();
                } else {
                    r = objectInputStream.readObject();
                }
                Assertions.assertEquals(e, r);
                real.add(r);
            }
            objectInputStream.close();
        }
        Assertions.assertEquals(newListObject(), real);
    }
}
