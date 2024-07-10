package org.example.eventTpoic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Configuration
public class TopicListenerEngineConfig {
    /**
     * 线程池异步处理
     */
    private static final Executor EXECUTOR = new ThreadPoolExecutor(
            20,
            50,
            10,
            TimeUnit.MINUTES,
            new LinkedBlockingQueue<>(500),
            new CustomizableThreadFactory("EVENT_ENGINE_POOL")
    );

    @Bean("eventListenerEngine")
    public TopicListenerEngine initEventListenerEngine() {
        Map<Topic, List<TopicListener>> toplicMap = new HashMap<>();
        // todo
        toplicMap.put(Topic.JOIN_MEMBERSHIP_GROUP, Arrays.asList(new TopicListener() {
            @Override
            public void onEvent(TopicEvent event) {
                log.info("Receive Event:{}", event);
            }
        }));
        toplicMap.put(Topic.ISSUE_COUPONS, Arrays.asList());
        toplicMap.put(Topic.SEND_WELCOME_MSG, Arrays.asList());
        return new TopicListenerEngine(EXECUTOR, toplicMap);
    }
}
