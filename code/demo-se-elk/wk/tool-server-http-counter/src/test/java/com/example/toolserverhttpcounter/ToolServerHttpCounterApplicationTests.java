package com.example.toolserverhttpcounter;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ToolServerHttpCounterApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolServerHttpCounterApplicationTests.class);

    @Test
    void contextLoads() {
        LOGGER.info("hello");
    }
}
