package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OverTest {
    private static final Logger logger = LoggerFactory.getLogger(OverTest.class);
    @Test
    void testOverLog4j() {
        logger.info("---- over start ----");
        Log4jService log4jService = new Log4jService();
        log4jService.doSomething();
        logger.info("---- over end ----");
    }
}
