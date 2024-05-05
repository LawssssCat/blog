package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.oxm.xstream.XStreamMarshaller;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Objects;

public class OxmTest {

    final static File FILE = new File(Objects.requireNonNull(OxmTest.class.getResource("/")).getFile(),
            OxmTest.class.getName() + ".xml");

    @Test
    void test() {
        XStreamMarshaller xStreamMarshaller = new XStreamMarshaller();

        Person person = new Person();
        person.setId("111");
        person.setName("222");
        person.setAge(333);

        try (OutputStream outputStream = new FileOutputStream(FILE)) {
            Result result = new StreamResult(outputStream);
            xStreamMarshaller.marshal(person, result);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(FILE.exists());
    }
}
