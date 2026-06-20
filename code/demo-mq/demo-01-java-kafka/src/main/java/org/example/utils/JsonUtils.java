package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonUtils {

    private static ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }

    public static Optional<String> writeValueAsString(Object obj) {
        try {
            String json = getObjectMapper().writeValueAsString(obj);
            return Optional.ofNullable(json);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<T> parseToObject(String msg, Class<T> clazz) {
        try {
            T obj = getObjectMapper().readValue(msg, clazz);
            return Optional.ofNullable(obj);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }
}
