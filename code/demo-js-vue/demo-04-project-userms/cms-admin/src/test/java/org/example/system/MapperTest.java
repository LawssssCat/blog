package org.example.system;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class MapperTest {
    @Resource
    private DataSource dataSource;

    @Test
    void test() {
        log.info("{}", dataSource);
        Assertions.assertThat(dataSource).isNotNull();
    }
}
