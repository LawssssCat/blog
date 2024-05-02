package org.example;

import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;

public class InitTest {
    private final static Logger logger = Logger.getLogger(InitTest.class);
    @Test
    void testLog4jInit() {
        /*
          缺少日志配置，所以提示下面信息：
          log4j:WARN No appenders could be found for logger (org.example.InitTest).
          log4j:WARN Please initialize the log4j system properly.
          log4j:WARN See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.
         */
        logger.debug("debug");
        logger.info("info");
    }
}
