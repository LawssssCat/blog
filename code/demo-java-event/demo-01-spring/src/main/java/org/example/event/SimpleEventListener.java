package org.example.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

@Slf4j
@Component
public class SimpleEventListener implements ApplicationListener<SimpleEvent> {
    @Resource
    private ApplicationContext applicationContext;

    @Override
    public void onApplicationEvent(SimpleEvent event) {
        log.info("receive event:{}, stamp:{}", event, event.getTimestamp());
        Optional.of(event).map(SimpleEvent::getName).filter(name -> 4 < name.length()).ifPresent(str -> {
            // shutdown
            SpringApplication.exit(applicationContext);
            System.exit(0);
        });
    }
}
