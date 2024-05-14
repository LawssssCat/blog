package org.example.job;

import org.example.listener.MyJobListener;
import org.junit.jupiter.api.Test;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;

public class ListenerTest extends AbstractSimpleJobTest {
    @Test
    void test() throws SchedulerException {
        startSchedule(CronJobTest.HelloJob.class,
                jobBuilder -> {},
                triggerTriggerBuilder -> {
                    triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(REPEAT).withIntervalInSeconds(1));
                },
                schedulerContext -> {
                    Scheduler scheduler = schedulerContext.getScheduler();
                    JobKey jobKey = schedulerContext.getJobKey();
                    try {
                        // listen
                        scheduler.getListenerManager().addJobListener(new MyJobListener());
                        // wait
                        while (true) {
                            if (!scheduler.checkExists(jobKey)) break;
                        }
                    } catch (SchedulerException e) {
                        throw new RuntimeException(e);
                    }
                });
    }
}
