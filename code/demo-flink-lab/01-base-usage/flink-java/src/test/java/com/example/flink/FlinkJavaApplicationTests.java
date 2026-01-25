package com.example.flink;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FlinkJavaApplicationTests {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlinkJavaApplicationTests.class);

    @Test
    void contextLoads() {
        LOGGER.info("hello world!");
    }

}
