package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.example.job.HelloJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class QuartzConfiguration {
    @Autowired
    private Scheduler scheduler;

    @PostConstruct
    public void helloJob() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(3).withIntervalInSeconds(1))
                .build();
        scheduler.scheduleJob(job, trigger);
    }

    @PostConstruct
    public void shutdownScheduler() {
        new Thread(() -> {
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            try {
                scheduler.shutdown(); // 在 spring 中，默认 scheduler 不会自动关闭
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

}
