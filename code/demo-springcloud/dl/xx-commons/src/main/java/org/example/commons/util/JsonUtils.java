package org.example.commons.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    // 只有在第一次调用 getObjectMapper() 时才会被 JVM 加载并初始化实例
    private static class ObjectMapperHolder {
        private static final ObjectMapper INSTANCE = new ObjectMapper();

        static {
            // 可以在这里统一配置好，防止后续被其他线程动态篡改
            // INSTANCE.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        }
    }

    public static ObjectMapper getObjectMapper() {
        return ObjectMapperHolder.INSTANCE;
    }
}
