package org.example.job;

import org.example.listener.MyJobListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;

public class ListenerTest extends AbstractSimpleJobTest {
    @Test
    void test() throws SchedulerException {
        MyJobListener myJobListener = new MyJobListener();
        startSchedule(CronJobTest.HelloJob.class,
                jobBuilder -> {},
                triggerTriggerBuilder -> {
                    triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(REPEAT).withIntervalInSeconds(1));
                },
                schedulerContext -> {
                    try {
                        // add listen
                        schedulerContext.getScheduler().getListenerManager().addJobListener(myJobListener);
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                    schedulerContext.startJobSchedule();
                });
        Assertions.assertEquals(2 * (REPEAT + 1), myJobListener.getCounter().get());
    }
}
