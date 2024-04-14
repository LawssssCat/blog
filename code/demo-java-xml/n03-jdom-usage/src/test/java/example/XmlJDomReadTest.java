package example;

import lombok.extern.slf4j.Slf4j;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.AbstractFilter;
import org.jdom2.input.SAXBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class XmlJDomReadTest {
    Document read;
    @BeforeEach
    void beforeEach() {
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            read = saxBuilder.build(getClass().getResource("/message001.xml"));
        } catch (JDOMException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    void test_getRootElement() {
        Element rootElement = read.getRootElement();
        Assertions.assertEquals("my-content", rootElement.getQualifiedName());
        Assertions.assertEquals(2, rootElement.getChild("posts").getChildren().size());
        Assertions.assertArrayEquals(new String[] {"P001", "P002"}, rootElement.getChild("posts").getChildren().stream().map(e -> e.getAttribute("id").getValue()).toArray());
    }

    static class ParentFilter extends AbstractFilter<Element> {
        private final Element p;
        ParentFilter(Element p) {
            this.p = p;
        }
        @Override
        public Element filter(Object content) {
            if(content instanceof Element) {
                Element e = (Element) content;
                if (p.equals(e.getParentElement())) {
                    return e;
                }
            }
            return null;
        }
    }

    @Test
    void test_nodeIterator() {
        int countContentElement = 0;
        int countPostElement = 0;
        List<String> contentElementQNameList = new ArrayList<>();
        List<String> postElementIdList = new ArrayList<>();
        // test
        Element rootElement = read.getRootElement();
        Iterator<Element> contentIterator = rootElement.getDescendants(new ParentFilter(rootElement));
        while (contentIterator.hasNext()) {
            countContentElement++;
            Element contentElement = contentIterator.next();
            System.out.println("contentElement = " + contentElement);
            contentElementQNameList.add(contentElement.getQualifiedName());
            if("posts".equals(contentElement.getQualifiedName())) {
                Iterator<Element> postIterator = contentElement.getDescendants(new ParentFilter(contentElement));
                while (postIterator.hasNext()) {
                    countPostElement++;
                    Element post = postIterator.next();
                    System.out.println("postElement = " + post);
                    postElementIdList.add(post.getAttributeValue("id"));
                }
            }
        }
        Assertions.assertEquals(4, countContentElement);
        Assertions.assertEquals(2, countPostElement);
        Assertions.assertArrayEquals(new String[] {"web-site", "owner", "description", "posts"}, contentElementQNameList.toArray());
        Assertions.assertArrayEquals(new String[] {"P001", "P002"}, postElementIdList.toArray());
    }
}
