package org.example.handler;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.EventNameEnum;
import org.example.model.param.EventFetchContext;
import org.example.model.param.EventFetchParam;
import org.example.model.po.EventPo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FetchEventHandler implements EventHandler {
    @Override
    public boolean isHandle(EventNameEnum eventName) {
        return EventNameEnum.FETCH.equals(eventName);
    }

    @Override
    public boolean handle(EventNameEnum eventName, EventPo eventPo) {
        EventFetchParam param = eventPo.getParamObj(EventFetchParam.class);
        EventFetchContext context = eventPo.getContextObj(EventFetchContext.class);
        log.info("handle fetch: {}, {}", param, context);
        log.info("handle fetch: fetch, {}", param.getAId());
        log.info("handle fetch: save 1, {}", param.getAId());
        log.info("handle fetch: save 2, {}", param.getAId());
        log.info("handle fetch: save 3, {}", param.getAId());
        return true;
    }

    @Override
    public void clean(EventNameEnum eventName, EventPo eventPo) {
        EventFetchParam param = eventPo.getParamObj(EventFetchParam.class);
        log.info("clean fetch: 1, {}", param.getAId());
        log.info("clean fetch: 2, {}", param.getAId());
        log.info("clean fetch: 3, {}", param.getAId());
    }
}
