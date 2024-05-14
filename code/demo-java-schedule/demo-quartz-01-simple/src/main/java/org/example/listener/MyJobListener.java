package org.example.listener;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Getter
public class MyJobListener implements JobListener {
    private final AtomicInteger counter = new AtomicInteger(0);
    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("监听：任务开始前 {}", counter.incrementAndGet());
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("监听：任务被取消 {}", counter.incrementAndGet());
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("监听：任务执行后 {}", counter.incrementAndGet());
    }
}
