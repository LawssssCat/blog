package org.example.model.po;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.example.constant.EventNameEnum;
import org.example.constant.EventStatusEnum;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Data
@FieldNameConstants
@NoArgsConstructor
public class EventPo {
    private Integer id;
    private Integer status;
    private String name;
    private String param;
    private String context;

    private static final ObjectMapper OBJECT_MAPPER = new Jackson2ObjectMapperBuilder()
            .featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
            .build();

    private static <T> T parseJsonObj(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toJsonString(Object obj) {
        if (obj == null || obj instanceof String) {
            return (String) obj;
        }
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T getParamObj(Class<T> clazz) {
        return parseJsonObj(param, clazz);
    }

    public <T> T getContextObj(Class<T> clazz) {
        return parseJsonObj(context, clazz);
    }

    public void setParamObj(Object obj) {
        this.param = toJsonString(obj);
    }

    public void setContextObj(Object obj) {
        this.context = toJsonString(obj);
    }

    public EventPo(Integer id) {
        this.id = id;
    }

    public EventPo(Integer id, EventStatusEnum status) {
        this.id = id;
        this.status = status.getVal();
    }

    public EventPo(EventNameEnum name) {
        this.name = name.name();
    }

    public EventPo(EventNameEnum name, Object param) {
        this.name = name.name();
        setParamObj(param);
    }

    public EventPo(EventNameEnum name, EventStatusEnum status) {
        this.name = name.name();
        this.status = status.getVal();
    }
}
