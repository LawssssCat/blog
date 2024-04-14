package org.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class XmlSaxReadTest {
    SAXParser saxParser;
    InputStream resourceAsStream;
    @BeforeEach
    void beforeEach() {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            resourceAsStream = getClass().getResourceAsStream("/message001.xml");
            saxParser = saxParserFactory.newSAXParser();
        } catch (ParserConfigurationException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() throws IOException, SAXException {
        // 解析过程会生成一系列事件
        saxParser.parse(resourceAsStream, new DefaultHandler() {
            int indent = 0;

            @Override
            public void startDocument() throws SAXException {
                log.info("============ 开始解析：" + this);
            }

            @Override
            public void endDocument() throws SAXException {
                log.info("============ 结束解析：" + this);
            }

            @Override
            public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                indent++;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("%"+(indent*2)+"s", ""));
                stringBuilder.append("<").append(qName);
                if(!uri.isEmpty()) {
                    stringBuilder.append(" uri=\"").append(uri).append("\"");
                }
                if(!localName.isEmpty()) {
                    stringBuilder.append(" localName=\"").append(localName).append("\"");
                }
                if(attributes.getLength()>0) {
                    stringBuilder.append(" attributes=\"");
                    for (int i = 0; i < attributes.getLength(); i++) {
                        stringBuilder.append("{")
                                .append("'type':'").append(attributes.getType(i)).append("'").append(", ")
                                .append("'qName':'").append(attributes.getQName(i)).append("'").append(", ")
                                .append("'uri':'").append(attributes.getURI(i)).append("'").append(", ")
                                .append("'value':'").append(attributes.getValue(i)).append("'")
                                .append("}");
                    }
                    stringBuilder.append("\"");
                }
                stringBuilder.append(">");
                log.info("startElement: {}", stringBuilder.toString());
            }

            @Override
            public void endElement(String uri, String localName, String qName) throws SAXException {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("%"+(indent*2)+"s", ""));
                stringBuilder.append("</").append(qName);
                if(!uri.isEmpty()) {
                    stringBuilder.append(" uri=\"").append(uri).append("\"");
                }
                if(!localName.isEmpty()) {
                    stringBuilder.append(" localName=\"").append(localName).append("\"");
                }
                stringBuilder.append(">");
                log.info("  endElement: {}", stringBuilder.toString());
                indent--;
            }

            // 文本数据回调
            @Override
            public void characters(char[] ch, int start, int length) throws SAXException {
                indent++;
                String content = String.valueOf(ch, start, length);
                if (!content.trim().isEmpty()) {
                    String stringBuilder = String.format("%" + (indent * 2) + "s", "") + content;
                    log.info("  characters: {}", stringBuilder);
                }
                indent--;
            }
        });
    }
}
