package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class DailyTimeIntervalTest implements Job {
    private final static AtomicInteger jobCounter = new AtomicInteger(0);

    @Test
    void test() throws SchedulerException, InterruptedException {
        int repeat = 2;
        DailyTimeIntervalScheduleBuilder dailyTimeIntervalScheduleBuilder = DailyTimeIntervalScheduleBuilder.dailyTimeIntervalSchedule()
                .withInterval(1, DateBuilder.IntervalUnit.SECOND) // 间隔
                .withRepeatCount(repeat); // 次数

        log.info("create JobDetail");
        JobDetail jobDetail = JobBuilder.newJob(DailyTimeIntervalTest.class)
                .withIdentity("interval-test", "quartz-test")
                .build();

        log.info("create Trigger");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(dailyTimeIntervalScheduleBuilder)
                .startNow() // 1次
                .build();

        log.info("create Scheduler");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        log.info("====== start Scheduler ======");
        scheduler.start();
        Assertions.assertEquals(0, jobCounter.get());

        log.info("====== registry Jobs ======");
        scheduler.scheduleJob(jobDetail, trigger);

        log.info("====== wait Jobs end ======");
        TimeUnit.SECONDS.sleep(repeat);

        log.info("====== Jobs end ======");
        Assertions.assertEquals(repeat + 1, jobCounter.get());
        scheduler.shutdown();
        Assertions.assertTrue(scheduler.isShutdown());
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("hello~{}", jobCounter.incrementAndGet());
        // 制造阻塞，观察异步情况 —— 默认异步执行，下一个任务不会被当前阻塞影响
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
