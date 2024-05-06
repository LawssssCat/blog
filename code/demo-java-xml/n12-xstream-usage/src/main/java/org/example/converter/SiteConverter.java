package org.example.converter;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.anno.Site;

@Slf4j
public class SiteConverter implements Converter {
    @Override
    public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
        if (source instanceof Site) {
            Site site = (Site) source;
            writer.startNode("my-url");
            writer.setValue(site.getUrl());
            writer.endNode();
            writer.startNode("my-id");
            writer.setValue(site.getId());
            writer.endNode();
        }
    }

    @Override
    public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
        Site site = new Site();
        while(reader.hasMoreChildren()) {
            reader.moveDown();
            switch (reader.getNodeName()) {
                case "my-url":
                    site.setUrl(reader.getValue());
                    break;
                case "my-id":
                    site.setId(reader.getValue());
            }
            reader.moveUp();
        }
        return site;
    }

    @Override
    public boolean canConvert(Class type) {
        return type.isAssignableFrom(Site.class);
    }
}
