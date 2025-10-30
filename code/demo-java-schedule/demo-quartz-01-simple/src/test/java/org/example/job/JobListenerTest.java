package org.example.job;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class JobListenerTest implements Job {
    private final static AtomicInteger jobCounter = new AtomicInteger(0);
    private final static AtomicInteger jobAfterCounter = new AtomicInteger(0);
    private final static AtomicInteger jobVetoedCounter = new AtomicInteger(0);
    private final static AtomicInteger jobMissCounter = new AtomicInteger(0);

    @Test
    void test() throws SchedulerException, InterruptedException {
        int repeat = 1; // 重复0次
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(1) // 间隔
                .withRepeatCount(repeat);// 次数

        log.info("create JobDetail");
        JobDetail jobDetail = JobBuilder.newJob(JobListenerTest.class)
                .withIdentity("simpleJob-test", "quartz-test")
                .usingJobData("msg", "hello-jobDetail")
                .build();

        log.info("create Trigger");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("simpleJob-test-trigger", "quartz-test")
                .withSchedule(simpleScheduleBuilder)
                .startNow() // 1次
                .usingJobData("msg", "hello-trigger")
                .build();

        log.info("create Scheduler");
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        log.info("add Job Listener");
        scheduler.getListenerManager().addJobListener(new JobListener() { // 监听回调
            @Override
            public String getName() {
                return getClass().getName();
            }
            // 调用前
            @Override
            public void jobToBeExecuted(JobExecutionContext context) {
                log.info("监听[{}][jobCounter={}]：Job开始前", context.getJobDetail().getKey(), jobCounter.incrementAndGet());
            }
            // 即将执行时被否决
            @Override
            public void jobExecutionVetoed(JobExecutionContext context) {
                log.info("监听[{}][jobVetoedCounter={}]：Job被取消", context.getJobDetail().getKey(), jobVetoedCounter.incrementAndGet());
            }
            // 调用后
            @Override
            public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
                log.info("监听[{}][jobAfterCounter={}]：Job执行后", context.getJobDetail().getKey(), jobAfterCounter.incrementAndGet());
            }
        });
        log.info("add Trigger Listener");
        scheduler.getListenerManager().addTriggerListener(new TriggerListener() {
            @Override
            public String getName() {
                return getClass().getName();
            }
            // 当与监听器相关联的Trigger被触发，Job上的execute()方法将被执行时，Scheduler就调用该方法。
            @Override
            public void triggerFired(Trigger trigger, JobExecutionContext context) {
                log.info("监听[{}][jobCounter={}]：Trigger触发前", context.getJobDetail().getKey(), jobCounter.incrementAndGet());
            }
            // 判断是否取消
            @Override
            public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
                log.info("监听[{}][jobVetoedCounter={}]：Trigger触发时，判断Job是否执行", context.getJobDetail().getKey(), jobVetoedCounter.incrementAndGet());
                return jobVetoedCounter.get() > 1;
            }
            // Scheduler 调用这个方法是在 Trigger 错过触发时。
            @Override
            public void triggerMisfired(Trigger trigger) {
                log.info("监听[{}][jobMissCounter={}]：Trigger错过", trigger.getJobKey(), jobMissCounter.incrementAndGet());
            }
            @Override
            public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
                log.info("监听[{}][jobAfterCounter={}]：Trigger触发后", context.getJobDetail().getKey(), jobAfterCounter.incrementAndGet());
            }
        });

        log.info("====== start Scheduler ======");
        scheduler.start();
        Assertions.assertEquals(0, jobCounter.get());

        log.info("====== registry Jobs ======");
        scheduler.scheduleJob(jobDetail, trigger);

        log.info("====== wait Jobs end ======");
        TimeUnit.SECONDS.sleep(repeat + 1);

        log.info("====== unregistry Jobs ======");
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals(3, jobCounter.get());
        Assertions.assertEquals(2, jobAfterCounter.get());
        Assertions.assertEquals(3, jobVetoedCounter.get());
        Assertions.assertEquals(0, jobMissCounter.get()); // todo

        log.info("====== Jobs end ======");
        scheduler.shutdown();
        Assertions.assertTrue(scheduler.isShutdown());
    }

    /**
     * 编写任务执行逻辑
     *
     * @param jobExecutionContext 获取调度器调用 job 时传递的参数，如 JobDetail
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        // do Nothing....
    }
}
