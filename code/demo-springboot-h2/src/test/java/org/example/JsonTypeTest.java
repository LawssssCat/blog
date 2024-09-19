package org.example;

import lombok.extern.slf4j.Slf4j;
import org.example.model.Event;
import org.example.model.EventPoParam;
import org.example.repository.EventMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@SpringBootTest
public class JsonTypeTest {
    @Resource
    private EventMapper eventMapper;

    @Test
    void test() {
        EventPoParam param = EventPoParam.builder().build();
        List<Event> events = eventMapper.findByParam(param);
        log.info("events: {}", events);
    }
}
