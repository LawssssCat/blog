package org.example.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

@Slf4j
public class MyJobListener implements JobListener {
    @Override
    public String getName() {
        return getClass().getName();
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("监听：任务开始前");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("监听：任务被取消");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("监听：任务执行后");
    }
}
