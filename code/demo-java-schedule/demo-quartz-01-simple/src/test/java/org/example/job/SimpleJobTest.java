package org.example.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

@Slf4j
public class SimpleJobTest {

    private final static String MSG_LOGO_01 = "hello~";
    private final static String MSG_LOGO_02 = "trigger-hello~";
    private final static String MSG_JOB_NAME = "jobNameHello";
    private final static String MSG_JOB_GROUP = "jobGroupDemo";
    private final static String MSG_TRIGGER_NAME = "triggerName01";
    private final static String MSG_TRIGGER_GROUP = "triggerGroup01";
    private static volatile int COUNT = 0;
    private static final int STEP = 2;
    private static final int REPEAT = 2;

    @Test
    void test() {
        log.info("创建任务实例");
        JobDetail jobHello = JobBuilder.newJob(HelloJob.class)
                .withIdentity(MSG_JOB_NAME, MSG_JOB_GROUP)
                .usingJobData("logo", MSG_LOGO_01) // 向任务传递参数
                .build();
        log.info("创建触发器");
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(MSG_TRIGGER_NAME, MSG_TRIGGER_GROUP)
                .usingJobData("logo", MSG_LOGO_02) // 向任务传递参数 | 若 jobDetail 中也有定义，优先使用 trigger 定义
                .usingJobData("step", STEP) // 向任务传递参数
                .startNow() // 马上调用一次，这次不计入 repeatCount 重复次数里
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(REPEAT))
                .build();
        log.info("创建调度器");
        Scheduler scheduler;
        try {
            scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.scheduleJob(jobHello, trigger);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        log.info("启动调度器（异步）");
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        log.info("启动调度器成功，监听测试");
        try {
            while (scheduler.checkExists(trigger.getKey())) {
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
        // 校验运行结果
        Assertions.assertEquals(STEP * (REPEAT+1), COUNT);
    }
    /**
     * 编写任务执行逻辑
     */
    public static class HelloJob implements Job {
        /**
         * trigger 传值或者 jobDetail 传值
         */
        @Setter
        private String logo;

        /**
         * @param jobExecutionContext 获取调度器调用 job 时传递的参数，如 JobDetail
         */
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            Assertions.assertEquals(MSG_JOB_NAME, jobDetail.getKey().getName());
            Assertions.assertEquals(MSG_JOB_GROUP, jobDetail.getKey().getGroup());

            Trigger trigger = jobExecutionContext.getTrigger();
            Assertions.assertEquals(MSG_TRIGGER_NAME, trigger.getKey().getName());
            Assertions.assertEquals(MSG_TRIGGER_GROUP, trigger.getKey().getGroup());

            // logo 优先级
            Assertions.assertEquals(MSG_LOGO_01, jobDetail.getJobDataMap().getString("logo"));
            Assertions.assertEquals(MSG_LOGO_02, trigger.getJobDataMap().getString("logo"));
            Assertions.assertEquals(MSG_LOGO_02, jobExecutionContext.getMergedJobDataMap().getString("logo"));
            Assertions.assertEquals(MSG_LOGO_02, this.logo); // 优先使用 trigger 传入数值

            // count
            int triggerStep = trigger.getJobDataMap().getInt("step");
            log.info("{} context: count={}, 本次执行={}, 下次执行={}",
                    this.logo, COUNT+=triggerStep,
                    jobExecutionContext.getFireTime(),
                    jobExecutionContext.getNextFireTime());
        }
    }
}
