package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.EventNameEnum;
import org.example.model.param.EventDeleteContext;
import org.example.model.param.EventDeleteParam;
import org.example.model.po.EventPo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DeleteEventHandler implements EventHandler {
    @Override
    public boolean isHandle(EventNameEnum eventName) {
        return EventNameEnum.DELETE.equals(eventName);
    }

    @Override
    public boolean handle(EventNameEnum eventName, EventPo eventPo) {
        EventDeleteParam param = eventPo.getParamObj(EventDeleteParam.class);
        EventDeleteContext context = eventPo.getContextObj(EventDeleteContext.class);
        log.info("handle delete: {}, {}}", param, context);
        if (!context.getIsRemoteOk()) {
            log.info("handle delete: delete remote");
            context.setIsRemoteOk(true);
        }
        if (!context.getIsLocalOk()) {
            log.info("handle delete: delete local");
            context.setIsLocalOk(true);
        }
        eventPo.setContextObj(context);
        return true;
    }

    @Override
    public void clean(EventNameEnum eventName, EventPo eventPo) {
        log.info("clean delete: do nothing");
    }
}
