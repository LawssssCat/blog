package org.example;

import org.apache.log4j.Logger;

public class Log4jService {
    private static final Logger logger = Logger.getLogger(Log4jService.class);
    public void doSomething() {
        logger.trace("Alpha doSomething trace");
        logger.debug("Alpha doSomething debug");
        logger.info("Alpha doSomething info");
        logger.warn("Alpha doSomething warn");
        logger.error("Alpha doSomething error");
    }
}
