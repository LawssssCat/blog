package org.example.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatusEnum {
    TODO(0),
    DONE(1),
    CANCEL(2),
    CLEAN(3);

    private int val;
}
