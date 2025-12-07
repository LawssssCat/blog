package com.example.toolserverhttpcounter.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private static ObjectMapper objectMapper;

    private static ObjectMapper getObjectMapper() {
        return objectMapper != null ? objectMapper : new ObjectMapper();
    }

    public static String parseToString(Object obj) throws JsonProcessingException {
        return getObjectMapper().writeValueAsString(obj);
    }
}
