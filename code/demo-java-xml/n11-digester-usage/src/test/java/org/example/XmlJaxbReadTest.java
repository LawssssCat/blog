package org.example;

import org.example.entity.JAXBMyContent;
import org.example.entity.JAXBPost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public class XmlJaxbReadTest {
    @Test
    void test() {
        JAXBMyContent myContent;
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(JAXBMyContent.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            myContent = (JAXBMyContent) unmarshaller.unmarshal(XmlJaxbWriteTest.FILE);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
        System.out.println("myContent = " + myContent);
        Assertions.assertNotNull(myContent.getOwner());
        Assertions.assertNotNull(myContent.getWebSite());
        Assertions.assertNotNull(myContent.getDescription());
        Assertions.assertEquals(2, myContent.getPosts().size());
        Assertions.assertArrayEquals(new String[] {"P000", "P001"},
                myContent.getPosts().stream().map(JAXBPost::getId).toArray());
        Assertions.assertArrayEquals(new String[] {"JAXP-TITLE-0", "JAXP-TITLE-1"},
                myContent.getPosts().stream().map(JAXBPost::getTitle).toArray());
    }
}
