package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.EventNameEnum;
import org.example.model.po.EventPo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class InitEventHandler implements EventHandler {
    @Override
    public boolean isHandle(EventNameEnum eventName) {
        return EventNameEnum.INIT.equals(eventName);
    }

    @Override
    public boolean handle(EventNameEnum eventName, EventPo eventPo) {
        log.info("handle init: fetch 1");
        log.info("handle init: fetch 2");
        log.info("handle init: fetch 3");
        return true;
    }

    @Override
    public void clean(EventNameEnum eventName, EventPo eventPo) {
        throw new UnsupportedOperationException();
    }
}
