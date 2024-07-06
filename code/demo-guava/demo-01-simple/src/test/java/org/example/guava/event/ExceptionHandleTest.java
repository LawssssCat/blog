package org.example.guava.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * 处理事件处理过程中的异常
 */
@Slf4j
public class ExceptionHandleTest {
    private EventBus eventBus = new EventBus(new SubscriberExceptionHandler() {
        @Override
        public void handleException(Throwable exception, SubscriberExceptionContext context) {
            log.error("eventBus: {}", context.getEventBus());
            log.error("event: {}", context.getEvent());
            log.error("event: {}", context.getEvent());
            log.error("subscriber: {}", context.getSubscriber());
            log.error("subscriberMethod: {}", context.getSubscriberMethod());
            exception.printStackTrace();
        }
    });

    @Test
    void testSubscribe() {
        eventBus.register(new Object() {
            @Subscribe
            public void doAction(String event) {
                log.info("receive: {}", event);
                throw new RuntimeException("xxxxxxxxxxxx");
            }
        });
        log.info("sending event");
        eventBus.post("Simple Event");
        log.info("sent event");
    }
}
