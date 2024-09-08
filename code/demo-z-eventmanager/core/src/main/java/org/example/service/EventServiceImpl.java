package org.example.service;

import com.google.common.base.Throwables;
import lombok.extern.slf4j.Slf4j;
import org.example.constant.EventNameEnum;
import org.example.constant.EventStatusEnum;
import org.example.handler.EventHandler;
import org.example.mapper.EventMapper;
import org.example.model.po.EventPo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EventServiceImpl implements EventService {
    @Resource
    private PlatformTransactionManager platformTransactionManager;

    @Resource
    private Map<String, EventHandler> eventHandlerMap;

    @Resource
    private EventMapper eventMapper;

    @Transactional
    @Override
    public Integer createEvent(EventNameEnum name, Object param, Object context) {
        EventPo eventPo = new EventPo();
        eventPo.setStatus(EventStatusEnum.TODO.getVal());
        eventPo.setName(name.name());
        eventPo.setParamObj(param);
        eventPo.setContextObj(context);
        int count = eventMapper.insert(eventPo);
        log.info("event create success, {}, {}, {}", eventPo.getId(), eventPo.getName(), count);
        return eventPo.getId();
    }

    @Override
    public boolean handleEvent(EventNameEnum eventName) {
        List<EventPo> eventList = eventMapper.selectByParam(new EventPo(eventName, EventStatusEnum.TODO));
        if (CollectionUtils.isEmpty(eventList)) {
            log.debug("handle event skip for no {} events", eventName);
            return true;
        }
        List<EventHandler> handlers = getEventHandlerList(eventName);
        AtomicInteger countDone = new AtomicInteger(0);
        boolean isAllDone = eventList.stream().map(event -> {
            boolean isDone = handleSingleEvent(eventName, event, handlers);
            if (isDone) {
                countDone.incrementAndGet();
            }
            return isDone;
        }).reduce(true, (a, b) -> a && b);
        log.info("handle event success, {}, isAllDone={}, {}, {}", eventName, isAllDone, eventList.size(), countDone);
        return isAllDone;
    }

    private List<EventHandler> getEventHandlerList(EventNameEnum eventName) {
        return eventHandlerMap.values().stream()
                .filter(handler -> handler.isHandle(eventName))
                .collect(Collectors.toList());
    }

    /**
     * 为每个单独的事件开启新事务处理
     */
    private boolean handleSingleEvent(EventNameEnum eventName, EventPo event, List<EventHandler> handlers) {
        TransactionTemplate transactionTemplate = new TransactionTemplate(platformTransactionManager);
        transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_NESTED);
        Boolean result = transactionTemplate.execute(status -> {
            // finish event directly, if status is canceled
            if (1 != updateEventStatus(event.getId(), EventStatusEnum.TODO, EventStatusEnum.TODO)) {
                log.warn("rollback event context because event status is not 'todo', {}", event.getId());
                return true;
            }
            boolean isDone = true;
            for (EventHandler handler : handlers) {
                Object savepoint = status.createSavepoint();
                try {
                    String oldContext = event.getContext();
                    boolean isOk = handler.handle(eventName, event);
                    int isUpdateContext = updateContext(event.getId(), oldContext, event.getContext());
                        log.info("handle event {} with {}, isOk={}", eventName, handler.getClass(), isOk);
                    if (log.isDebugEnabled()) {
                        log.debug("{}, {}, {}", isUpdateContext, event.getParam(), event.getContext());
                    }
                    isDone = isDone && isOk;
                } catch (Throwable t) {
                    log.error("fail to handle event " + eventName + " with " + handler.getClass(), t);
                    status.rollbackToSavepoint(savepoint);
                    isDone = false;
                }
            }
            // rollback if event is canceled
            if (1 != updateEventStatus(event.getId(), EventStatusEnum.TODO, EventStatusEnum.TODO)) {
                log.warn("rollback event context because event status is not 'todo', {}", event.getId());
                platformTransactionManager.rollback(status);
                return true;
            }
            if (isDone) { // all handler is ok
                int count = updateEventStatus(event.getId(), EventStatusEnum.DONE, EventStatusEnum.TODO);
                log.info("handle event finish, {}, {}, {}", eventName, event.getId(), count);
            } else {
                log.debug("handle event remaining tasks ..., {}, {}", eventName, event.getId());
            }
            return isDone;
        });
        return Boolean.TRUE.equals(result);
    }

    private int updateContext(Integer id, String oldContext, String context) {
        int count = 0;
        if (!Objects.equals(oldContext, context)) {
            EventPo eventPo = new EventPo(id);
            eventPo.setContext(context);
            count = eventMapper.updateById(eventPo);
            log.info("update event context, {}, {}", id, context);
        }
        return count;
    }

    private int updateEventStatus(Integer id, EventStatusEnum status, EventStatusEnum... inStatus) {
        int count = 0;
        Optional<EventPo> now = eventMapper.selectByParamForUpdate(new EventPo(id)).stream().findFirst();
        if (now.isPresent()) {
            for (EventStatusEnum eventStatusEnum : inStatus) {
                if (eventStatusEnum.getVal() == now.get().getStatus()) {
                    count = eventMapper.updateById(new EventPo(id, status));
                }
            }
        }
        log.debug("update event status, {}, {}, {}", id, status, count);
        return count;
    }

    @Transactional
    @Override
    public void cancelEvent(EventNameEnum eventName, Object param) {
        List<EventPo> eventList = eventMapper.selectByParam(new EventPo(eventName, param));
        if (CollectionUtils.isEmpty(eventList)) {
            log.debug("cancel event skip for no {} events with {}", eventName, param);
            return;
        }
        eventList.forEach(event -> {
            int count = updateEventStatus(event.getId(), EventStatusEnum.CANCEL, EventStatusEnum.TODO);
            log.info("cancel event, {}, {}, {}", eventName, event.getId(), count);
        });
    }

    @Transactional
    @Override
    public void cleanEventData(EventNameEnum eventName, Object param) {
        List<EventPo> eventList = eventMapper.selectByParam(new EventPo(eventName, param));
        if (CollectionUtils.isEmpty(eventList)) {
            log.debug("clean event skip for no {} events with {}", eventName, param);
            return;
        }
        List<EventHandler> handlers = getEventHandlerList(eventName);
        eventList.forEach(event -> {
            int count = updateEventStatus(event.getId(), EventStatusEnum.CLEAN, new EventStatusEnum[]{
                    EventStatusEnum.TODO,
                    EventStatusEnum.DONE,
                    EventStatusEnum.CANCEL
            });
            log.info("clean event data, {}, {}, {}", eventName, event.getId(), count);
            for (EventHandler handler : handlers) {
                try {
                    handler.clean(eventName, event);
                    log.debug("clean event data, {}, {}, {}", eventName, event.getId(), handler.getClass());
                } catch (Throwable t) {
                    log.error("clean event data fail, {}, {}, {}", eventName, event.getId(), handler.getClass());
                    Throwables.throwIfUnchecked(t);
                }
            }
        });
    }
}
