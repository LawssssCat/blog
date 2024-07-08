package org.example.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleEventAnnotationListener {
    @Order(value = 0) // 优先级。值越低，优先级越高。 💡只与同类比较
    @Async
    @EventListener(condition = "#event.name == 'EID7'")
    // @EventListener({SimpleEvent.class}) // 指定多个 event
    public void listener001(SimpleEvent event) {
        log.info("receive event:{}", event);
    }

    /**
     * 事件二次发布！
     */
    @EventListener(classes = {SimpleEvent.class}, condition = "#event.name != 'RID'")
    public SimpleEvent listener002(SimpleEvent event) {
        if ("EID8".equals(event.getName())) {
            log.info("recall event:{}, stamp:{}", event, event.getTimestamp());
            SimpleEvent newEvent = new SimpleEvent("OK");
            newEvent.setName("RID");
            return newEvent;
        }
        return null;
    }
}
