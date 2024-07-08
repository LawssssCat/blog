package org.example.event;

import lombok.*;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SimpleEvent extends ApplicationEvent {
    private String name;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public SimpleEvent(Object source) {
        super(source);
    }
}
