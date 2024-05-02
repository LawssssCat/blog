package org.example;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitTest {
    private static final Logger logger = LoggerFactory.getLogger(InitTest.class);
    @Test
    void testInit() {
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
