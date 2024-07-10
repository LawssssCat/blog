package org.example.eventTpoic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class TopicEventPublisher implements ApplicationRunner, ApplicationEventPublisherAware {

    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            TopicEvent event = new TopicEvent(Topic.JOIN_MEMBERSHIP_GROUP, "Hello world!");
            log.info("Publish Event: {}", event);
            applicationEventPublisher.publishEvent(event);
        }, 0, 1, TimeUnit.SECONDS);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
