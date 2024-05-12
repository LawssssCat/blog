package org.example.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Calendar;

@Slf4j
public class SimpleJobStateTest {
    private static final String MSG_COUNT = "count";
    private static final int REPEAT = 3;

    @Test
    void testTransient() {

        JobDetail transientJob = JobBuilder.newJob(TransientJob.class).build();

        JobDetail persistJob = JobBuilder.newJob(PersistJob.class).build();

        Calendar triggerStartCalendar = Calendar.getInstance();
        triggerStartCalendar.add(Calendar.SECOND, 1); // 一秒后启动 trigger
        Trigger trigger = TriggerBuilder.newTrigger()
                .startAt(triggerStartCalendar.getTime())
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(REPEAT))
                .build();
        Scheduler scheduler;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if (!scheduler.checkExists(trigger.getKey())) break;
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }
        Assertions.assertEquals(REPEAT, persistJob.getJobDataMap().get(MSG_COUNT));
        Assertions.assertEquals(0, transientJob.getJobDataMap().get(MSG_COUNT));
    }
    /**
     * 无状态
     */
    @Getter
    @Setter
    public static class TransientJob implements Job {
        private int count = 0;
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            context.getJobDetail().getJobDataMap().put(MSG_COUNT, ++count);
            log.info("transient: {}", count);
        }
    }
    /**
     * 有状态
     */
    @Getter
    @Setter
    @PersistJobDataAfterExecution // 持久化数据
    public static class PersistJob implements Job {
        private int count = 0;
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            context.getJobDetail().getJobDataMap().put(MSG_COUNT, ++count);
            log.info("persist: {}", count);
        }
    }
}
