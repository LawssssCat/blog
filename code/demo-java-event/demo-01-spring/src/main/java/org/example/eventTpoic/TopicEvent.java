package org.example.eventTpoic;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class TopicEvent extends ApplicationEvent {
    private Topic topic;

    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public TopicEvent(Topic topic, Object source) {
        super(source);
        this.topic = topic;
    }
}
