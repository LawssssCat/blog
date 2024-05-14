package org.example.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

@Slf4j
public abstract class AbstractSimpleJobTest {
    final static String MSG_JOB_NAME = "jobNameHello";
    final static String MSG_JOB_GROUP = "jobGroupDemo";
    private final static AtomicInteger jobCounter = new AtomicInteger(0);
    /**
     * 任务间隔时间
     */
    static final int INTERVAL_SECOND = 1;
    /**
     * 任务重复次数
     */
    static final int REPEAT = 2;

    static Scheduler globalScheduler;

    @BeforeAll
    static void beforeAll() throws SchedulerException {
        log.info("创建调度器");
        globalScheduler = StdSchedulerFactory.getDefaultScheduler(); // 默认读 quartz.properties 配置
        // new StdSchedulerFactory(properties or fileName).getScheduler(); // 从自定义配置/配置文件中创建
        log.info("启动调度器（异步）");
        try {
            globalScheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    void startSchedule(Class<? extends Job> clazz,
                       Consumer<JobBuilder> jobBuilderConsumer,
                       Consumer<TriggerBuilder<Trigger>> triggerBuilderConsumer,
                       Consumer<SchedulerContext> schedulerConsumer) throws SchedulerException {
        log.info("创建任务实例");
        JobBuilder jobBuilder = JobBuilder.newJob(clazz)
                .withIdentity(MSG_JOB_NAME + "-" + jobCounter.getAndIncrement(), "group1");
        jobBuilderConsumer.accept(jobBuilder);
        JobDetail jobDetail = jobBuilder.build();
        log.info("创建触发器");
        TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilderConsumer.accept(triggerTriggerBuilder);
        Trigger trigger = triggerTriggerBuilder.build();
        log.info("下发调度任务 {}", jobDetail.getKey());
        SchedulerContext schedulerContext = new SchedulerContext(globalScheduler, jobDetail, trigger);
        schedulerConsumer.accept(schedulerContext);
        log.info("调度结束");
    }

    void startSchedule(Class<? extends Job> clazz,
                       Consumer<JobBuilder> jobBuilderConsumer,
                       Consumer<TriggerBuilder<Trigger>> triggerBuilderConsumer) throws SchedulerException {
        startSchedule(clazz, jobBuilderConsumer, triggerBuilderConsumer, SchedulerContext::startJobSchedule);
    }

    @AllArgsConstructor
    @Getter
    static class SchedulerContext {
        private Scheduler scheduler;
        private JobDetail job;
        private Trigger trigger;
        public void deliverJob() throws SchedulerException {
            log.info("下发调度任务 {}", job.getKey());
            scheduler.scheduleJob(job, trigger);
        }
        public void awaitJobEnd() throws SchedulerException {
            log.info("添加阻塞，等待调度结束");
            while (true) {
                if (!scheduler.checkExists(trigger.getKey())) break;
            }
            log.info("关闭任务调度");
            scheduler.shutdown();
        }
        public void startJobSchedule() {
            try {
                deliverJob();
                awaitJobEnd();
            } catch (SchedulerException e) {
                log.error("调度任务调度失败", e);
            }
        }
    }
}
