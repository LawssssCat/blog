package org.example.cache.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class H2CacheContextTest {
    private H2CacheContext h2CacheContext;

    @BeforeEach
    void beforeEach() throws Exception {
        h2CacheContext = new H2CacheContext();
    }

    @Test
    void test_setObject_and_getObject() {
        String key = "1";
        HashMap<String, Object> map = new HashMap<>();
        map.put("a", "1");
        map.put("b", 2);
        h2CacheContext.setObject(key, map);
        h2CacheContext.setObject(key, map, 10, TimeUnit.MINUTES);
        Map<String, Object> value = h2CacheContext.getObject(key, Map.class);
        log.info("cache value: {}", value);
    }
}
