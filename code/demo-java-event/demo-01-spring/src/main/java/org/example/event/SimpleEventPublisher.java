package org.example.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class SimpleEventPublisher implements ApplicationRunner {
    @Resource
    ApplicationContext applicationContext; // 实现了 applicationEventPublisher 接口

    @Resource
    private ApplicationEventPublisher applicationEventPublisher; // 接口

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.debug("context == publisher: {}", applicationEventPublisher == applicationContext);
        AtomicInteger counter = new AtomicInteger(0);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            SimpleEvent event = new SimpleEvent("hello world!");
            event.setName("EID" + counter.getAndIncrement());
            applicationContext.publishEvent(event);
        }, 0, 1, TimeUnit.SECONDS);
    }
}
