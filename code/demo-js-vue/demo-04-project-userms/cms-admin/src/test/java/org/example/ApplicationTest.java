package org.example;

import javax.annotation.Resource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ApplicationTest {
    @Resource
    private ApplicationContext applicationContext;

    @Test
    void test() {
        log.info("{}", applicationContext);
        Assertions.assertThat(applicationContext).isNotNull();
    }
}
