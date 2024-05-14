package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.example.listener.MyJobListener;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;

import java.util.Calendar;
import java.util.function.Consumer;

@Slf4j
public class CronJobTest extends AbstractSimpleJobTest {
    @Test
    void test() throws SchedulerException {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        MyJobListener myJobListener = new MyJobListener();
        startSchedule(HelloJob.class,
                jobBuilder -> {},
                triggerTriggerBuilder -> {
                    triggerTriggerBuilder.withSchedule(cronScheduleBuilder);
                    Calendar calender = Calendar.getInstance();
                    calender.add(Calendar.SECOND, REPEAT);
                    triggerTriggerBuilder.endAt(calender.getTime());
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

    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            log.info("hello~");
        }
    }
}
