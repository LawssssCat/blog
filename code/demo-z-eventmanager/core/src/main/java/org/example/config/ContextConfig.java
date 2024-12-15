package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.constant.EventNameEnum;
import org.example.model.param.EventDeleteContext;
import org.example.model.param.EventDeleteParam;
import org.example.model.param.EventFetchContext;
import org.example.model.param.EventFetchParam;
import org.example.service.EventService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionService;
import java.util.concurrent.TimeUnit;

@Slf4j
@EnableScheduling
@Configuration
public class ContextConfig implements ApplicationRunner {
    @Resource
    private EventService eventService;

    /**
     * 模拟数据消费 启动时
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("hello, bootstrap");
        eventService.handleEvent(EventNameEnum.INIT);
    }

    /**
     * 模拟数据消费 运行时
     */
    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void consumeSchedule() {
        log.info("hello, runtime");
        cancel();
        boolean flag = eventService.handleEvent(EventNameEnum.DELETE) &&
                eventService.handleEvent(EventNameEnum.FETCH);
    }

    private void cancel() {
        CompletableFuture.runAsync(() -> {
            int x = new Random(new Date().getTime()).nextInt(10);
            if (x > 3) {
                EventFetchParam param = new EventFetchParam("RT_1");
                eventService.cancelEvent(EventNameEnum.FETCH, param);
            }
        });
    }

    /**
     * 模拟数据创建
     */
    @Scheduled(initialDelay = 1, fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void productASchedule() {
        log.info("hello, productA");

        EventFetchParam param = new EventFetchParam("RT_1");
        EventFetchContext context = new EventFetchContext();
        int eventId = eventService.createEvent(EventNameEnum.FETCH, param, context);
    }

    /**
     * 模拟数据创建
     */
    @Scheduled(initialDelay = 2, fixedDelay = 3, timeUnit = TimeUnit.SECONDS)
    public void productBSchedule() {
        log.info("hello, productB");

        EventDeleteParam param = new EventDeleteParam("RT_1");
        EventDeleteContext context = new EventDeleteContext();
        eventService.createEvent(EventNameEnum.DELETE, param, context);
    }

    /**
     * 模拟数据创建
     */
    // @Scheduled(initialDelay = 10, fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void cleanSchedule() {
        log.info("hello, clean");
        {
            EventFetchParam param = new EventFetchParam("RT_1");
            eventService.cleanEventData(EventNameEnum.FETCH, param);
        }
    }
}
