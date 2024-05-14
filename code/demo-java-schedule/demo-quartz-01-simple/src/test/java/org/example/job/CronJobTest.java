package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.quartz.*;

import java.util.Calendar;
import java.util.function.Consumer;

@Slf4j
public class CronJobTest extends AbstractSimpleJobTest {
    @Test
    void test() throws SchedulerException {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/1 * * * * ?");
        startSchedule(HelloJob.class,
                new Consumer<JobBuilder>() {
                    @Override
                    public void accept(JobBuilder jobBuilder) {
                    }
                },
                new Consumer<TriggerBuilder<Trigger>>() {
                    @Override
                    public void accept(TriggerBuilder<Trigger> triggerTriggerBuilder) {
                        triggerTriggerBuilder.withSchedule(cronScheduleBuilder);
                        Calendar calender = Calendar.getInstance();
                        calender.add(Calendar.SECOND, 3);
                        triggerTriggerBuilder.endAt(calender.getTime());
                    }
                });
    }

    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            log.info("hello~");
        }
    }
}
