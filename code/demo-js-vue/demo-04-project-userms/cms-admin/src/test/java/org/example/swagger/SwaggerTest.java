package org.example.swagger;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.swagger.v3.core.converter.ModelConverters;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class SwaggerTest {
    @Test
    void xx() {
        System.out.println("hello world!");
        Map<String, io.swagger.v3.oas.models.media.Schema> stringSchemaMap = ModelConverters.getInstance().readAll(Xxx.class);
        System.out.println("stringSchemaMap = " + stringSchemaMap);
    }

    @Data
    public static class Xxx {
        @Schema(required = true)
        private String name;
    }
}
