package org.example.eventTpoic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

@Slf4j
public class TopicListenerEngine {

    /**
     * 事件执行线程池
     */
    private Executor executor;

    /**
     * 监听器
     */
    private Map<Topic, List<TopicListener>> topicListMap;

    public TopicListenerEngine(Executor executor, Map<Topic, List<TopicListener>> topicListMap) {
        this.executor = executor;
        this.topicListMap = topicListMap;
    }

    // todo @EventListener(condition = "") // SpEL
    @EventListener
    public void listen(TopicEvent event) {
        List<TopicListener> topicListeners = topicListMap.get(event.getTopic());
        topicListeners.forEach(listener -> {
            executor.execute(() -> {
                if (log.isDebugEnabled()) {
                    log.debug("Event Topic:{}, Handler:{}", event, listener);
                }
                listener.onEvent(event);
            });
        });
    }

}
