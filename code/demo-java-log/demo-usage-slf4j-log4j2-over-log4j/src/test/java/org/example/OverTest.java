package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverTest {
    private static final Logger logger = LoggerFactory.getLogger(OverTest.class);
    @Test
    void overTest() {
        logger.info("---------- log4j -----------");
        new Log4jService().doSomething();
        logger.info("---------- logback -----------");
        new LogbackService().doSomething();
    }
}
