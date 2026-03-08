package org.example.guava.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class SimpleEventBusTest {
    private EventBus eventBus = new EventBus();

    @Test
    void testSubscribe() {
        eventBus.register(new Object() {
            @Subscribe
            public void doAction(String event) {
                log.info("receive: {}", event);
            }
        });
        log.info("sending event");
        eventBus.post("Simple Event");
        log.info("sent event");
    }

    /**
     * 根据监听的入参，进行不同的监听
     */
    @Test
    void testSubscribeDifferenceClass() {
        eventBus.register(new Object() {
            @Subscribe
            public void doActionString(String event) {
                log.info("receive String: {}", event);
            }
            @Subscribe
            public void doActionInteger(Integer event) {
                log.info("receive Integer: {}", event);
            }
            @Subscribe
            public void doActionEventAcg(EventAcg event) {
                log.info("receive EventAcg: {}", event);
            }
        });

        eventBus.post("hello world!");
        eventBus.post(1);
        eventBus.post(new EventAcg("oh"));
    }

    @AllArgsConstructor
    @Data
    public static class EventAcg {
        private String name;
    }
}
