package org.example.guava.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 同步多线程的事件处理
 */
@Slf4j
public class SyncEventBusTest {
    private EventBus eventBus = new EventBus();

    @Test
    void testSubscribe() {
        eventBus.register(new Object() {
            @Subscribe
            public void doAction(String event) {
                log.info("receive: {}", event);
            }
        });
        log.info("prepare event");
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> simpleEvent = Stream.iterate(0, n -> n + 1).limit(10).map(n -> (Callable<Integer>) () -> {
            log.info("post: {}", n);
            eventBus.post("Simple Event " + n); // 💡info 和 post 输出在同一线程，说明默认是直接调用方法
            return n;
        }).collect(Collectors.toList());
        log.info("sending event");
        try {
            executorService.invokeAll(simpleEvent);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("sent event");
    }
}
