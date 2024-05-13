package org.example.job;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.function.Consumer;

@Slf4j
public abstract class AbstractSimpleJobTest {
    /**
     * 任务间隔时间
     */
    static final int INTERVAL_SECOND = 1;
    /**
     * 任务重复次数
     */
    static final int REPEAT = 2;

    void startSchedule(Class<? extends Job> clazz,
                       Consumer<JobBuilder> jobBuilderConsumer,
                       Consumer<TriggerBuilder<Trigger>> triggerBuilderConsumer,
                       Consumer<SchedulerContext> schedulerConsumer) throws SchedulerException {
        log.info("创建任务实例");
        JobBuilder jobBuilder = JobBuilder.newJob(clazz);
        jobBuilderConsumer.accept(jobBuilder);
        JobDetail jobDetail = jobBuilder.build();
        log.info("创建触发器");
        TriggerBuilder<Trigger> triggerTriggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilderConsumer.accept(triggerTriggerBuilder);
        Trigger trigger = triggerTriggerBuilder.build();
        log.info("创建调度器");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
        SchedulerContext schedulerContext = new SchedulerContext(scheduler, jobDetail.getKey(), trigger.getKey());
        log.info("启动调度器（异步）");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        log.info("启动调度器成功，监听测试");
        schedulerConsumer.accept(schedulerContext);
        log.info("调度结束");
    }

    void startSchedule(Class<? extends Job> clazz,
                       Consumer<JobBuilder> jobBuilderConsumer,
                       Consumer<TriggerBuilder<Trigger>> triggerBuilderConsumer) throws SchedulerException {
        startSchedule(clazz, jobBuilderConsumer, triggerBuilderConsumer, schedulerContext -> {
            Scheduler scheduler = schedulerContext.getScheduler();
            while (true) {
                try {
                    if (!scheduler.checkExists(schedulerContext.getTriggerKey())) break;
                } catch (SchedulerException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @AllArgsConstructor
    @Getter
    static class SchedulerContext {
        private Scheduler scheduler;
        private JobKey jobKey;
        private TriggerKey triggerKey;
    }
}
