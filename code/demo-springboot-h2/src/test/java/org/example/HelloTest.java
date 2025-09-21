package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DemoH2SpringbootApplication.class})
public class HelloTest {
    private static final Logger logger = LoggerFactory.getLogger("xxx");

    @Test
    void test() {
        System.out.println(logger.isDebugEnabled());
        logger.info("info xxx");
        logger.debug("debug xxx");
    }
}
