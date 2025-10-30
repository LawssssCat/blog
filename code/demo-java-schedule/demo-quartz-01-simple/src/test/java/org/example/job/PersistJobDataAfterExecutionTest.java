package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@PersistJobDataAfterExecution // 持久化 JobDataMap 数据
@DisallowConcurrentExecution // 通常同 @PersistJobDataAfterExecution 一起使用，避免当同一个 job（JobDetail） 的两个实例被并发执行时，由于竞争导致 JobDataMap 中存储的数据不确定的
public class PersistJobDataAfterExecutionTest implements Job {
    private final static AtomicInteger jobCounter = new AtomicInteger(0);

    @Test
    void test() throws SchedulerException, InterruptedException {
        int repeat = 1;
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1) // 间隔
                .withRepeatCount(repeat);// 次数

        log.info("create JobDetail");
        JobDetail jobDetail = JobBuilder.newJob(PersistJobDataAfterExecutionTest.class)
                .usingJobData("count", 0)
                .build();

        log.info("create Trigger");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(simpleScheduleBuilder)
                .startNow() // 1次
                .usingJobData("count", 0)
                .build();

        log.info("create Scheduler");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

        log.info("====== start Scheduler ======");
        scheduler.start();
        Assertions.assertEquals(0, jobCounter.get());

        log.info("====== registry Jobs ======");
        scheduler.scheduleJob(jobDetail, trigger);

        log.info("====== wait Jobs end ======");
        TimeUnit.SECONDS.sleep(repeat + 1);

        log.info("====== Jobs end ======");
        Assertions.assertEquals(repeat + 1, jobCounter.get());
        scheduler.shutdown();
        Assertions.assertTrue(scheduler.isShutdown());
    }

    /**
     * 编写任务执行逻辑
     *
     * @param jobExecutionContext 获取调度器调用 job 时传递的参数，如 JobDetail
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        int n = jobCounter.get();
        log.info("~~~~~~~~~~{}", n);
        // job detail
        JobDetail jobDetail = jobExecutionContext.getJobDetail();

        // trigger
        Trigger trigger = jobExecutionContext.getTrigger();

        // data map 优先级
        Assertions.assertEquals(n, getAndIncrement("count", jobDetail.getJobDataMap())); // 持久化，值会继承上一次Job
        Assertions.assertEquals(0, getAndIncrement("count", trigger.getJobDataMap())); // 不会继承

        // count
        log.info("getFireTime={}, getNextFireTime={}, count={}",
                jobExecutionContext.getFireTime(),
                jobExecutionContext.getNextFireTime(),
                jobCounter.incrementAndGet());
    }

    private int getAndIncrement(String key, JobDataMap map) {
        int value = map.getInt(key);
        map.put(key, value + 1);
        return value;
    }
}
