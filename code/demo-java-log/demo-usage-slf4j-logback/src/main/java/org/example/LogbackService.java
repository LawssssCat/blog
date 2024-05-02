package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackService {
    private static final Logger logger = LoggerFactory.getLogger(LoggerFactory.class);
    void doSomething() {
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
