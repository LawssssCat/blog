package org.example;

import com.thoughtworks.xstream.security.TypeHierarchyPermission;
import org.junit.jupiter.api.*;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Objects;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OxmTest {

    File FILE = new File(Objects.requireNonNull(getClass().getResource("/")).getFile(),
            getClass().getName() + ".xml");

    static Marshaller marshaller;
    static Unmarshaller unmarshaller;

    @BeforeAll
    static void beforeAll() {
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();
        xStreamMarshaller.setTypePermissions(new TypeHierarchyPermission(Person.class));
        // 既是 marshaller 也是 unmarshaller
        marshaller = xStreamMarshaller;
        unmarshaller = xStreamMarshaller;
    }

    private Person newPerson() {
        Person person = new Person();
        person.setId("111");
        person.setName("222");
        person.setAge(333);
        return person;
    }

    /**
     * 输出为
     */
    @Test
    @Order(1)
    void testMarshal() {
        try (OutputStream outputStream = Files.newOutputStream(FILE.toPath())) {
            Result result = new StreamResult(outputStream);
            marshaller.marshal(newPerson(), result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(FILE.exists());
    }

    @Test
    @Order(2)
    void testUnmarshal() {
        Object unmarshal = null;
        try (InputStream inputStream = Files.newInputStream(FILE.toPath())) {
            Source result = new StreamSource(inputStream);
            unmarshal = unmarshaller.unmarshal(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertEquals(newPerson(), unmarshal);
    }
}
