package org.example.model.param;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EventDeleteContext {
    private Boolean isRemoteOk;
    private Boolean isLocalOk;

    public EventDeleteContext() {
        this(false, false);
    }
}
