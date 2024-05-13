package org.example.job;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;

import java.util.concurrent.TimeUnit;

@Slf4j
public class SimpleJobTest extends AbstractSimpleJobTest {
    final static String KEY_LOGO = "logo";
    final static String MSG_LOGO_01 = "hello~";
    final static String MSG_LOGO_02 = "trigger-hello~";
    final static String MSG_JOB_NAME = "jobNameHello";
    final static String MSG_JOB_GROUP = "jobGroupDemo";
    final static String MSG_TRIGGER_NAME = "triggerName01";
    final static String MSG_TRIGGER_GROUP = "triggerGroup01";
    // 计数器
    private static volatile int COUNT = 0;
    // 计数器跨步
    private static final int STEP = 2;

    @Test
    void test() throws SchedulerException {
        startSchedule(SimpleJobTest.HelloJob.class,
                jobBuilder -> {
                    jobBuilder.withIdentity(MSG_JOB_NAME, MSG_JOB_GROUP);
                    jobBuilder.usingJobData(KEY_LOGO, MSG_LOGO_01);// 向任务传递参数
                },
                triggerBuilder -> {
                    triggerBuilder.withIdentity(MSG_TRIGGER_NAME, MSG_TRIGGER_GROUP);
                    triggerBuilder.usingJobData(KEY_LOGO, MSG_LOGO_02); // 向任务传递参数 | 若 jobDetail 中也有定义，优先使用 trigger 定义
                    // 计数
                    triggerBuilder.usingJobData("step", STEP); // 向任务传递参数
                    triggerBuilder.startNow(); // 马上调用一次，这次不计入 repeatCount 重复次数里
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(INTERVAL_SECOND).withRepeatCount(REPEAT));
                });
        // 校验运行结果
        Assertions.assertEquals(STEP * (REPEAT+1), COUNT);
    }
    /**
     * 编写任务执行逻辑
     */
    // @DisallowConcurrentExecution
    public static class HelloJob implements Job {
        private static Integer prevHashCode = null;
        /**
         * trigger 传值或者 jobDetail 传值
         */
        @Setter
        private String logo = "HelloJob";

        /**
         * @param jobExecutionContext 获取调度器调用 job 时传递的参数，如 JobDetail
         */
        @Override
        public void execute(JobExecutionContext jobExecutionContext) {
            // job detail
            JobDetail jobDetail = jobExecutionContext.getJobDetail();
            Assertions.assertEquals(MSG_JOB_NAME, jobDetail.getKey().getName());
            Assertions.assertEquals(MSG_JOB_GROUP, jobDetail.getKey().getGroup());

            // trigger
            Trigger trigger = jobExecutionContext.getTrigger();
            Assertions.assertEquals(MSG_TRIGGER_NAME, trigger.getKey().getName());
            Assertions.assertEquals(MSG_TRIGGER_GROUP, trigger.getKey().getGroup());

            // data map 优先级
            Assertions.assertEquals(MSG_LOGO_01, jobDetail.getJobDataMap().getString("logo"));
            Assertions.assertEquals(MSG_LOGO_02, trigger.getJobDataMap().getString("logo"));
            Assertions.assertEquals(MSG_LOGO_02, jobExecutionContext.getMergedJobDataMap().getString("logo"));
            Assertions.assertEquals(MSG_LOGO_02, this.logo); // 优先使用 trigger 传入数值

            // count
            int triggerStep = trigger.getJobDataMap().getInt("step");
            log.info("{} {} context: count={}, 本次执行={}, 下次执行={}",
                    this.logo,
                    this.hashCode() + // 不同
                            "/" + jobExecutionContext.hashCode() + // 不同
                            "/" + jobExecutionContext.getJobDetail().hashCode() + // ❗相同
                            "/" + jobExecutionContext.getTrigger().hashCode() + // ❗相同
                            "/" + jobExecutionContext.getJobDetail().getJobDataMap().hashCode() + // ❗相同
                            "/" + jobExecutionContext.getTrigger().getJobDataMap().hashCode() // ❗相同
                    ,
                    COUNT+=triggerStep,
                    jobExecutionContext.getFireTime(),
                    jobExecutionContext.getNextFireTime());

            // hashcode
            Assertions.assertNotEquals(prevHashCode, this.hashCode()); // 每次创建新的对象
            prevHashCode = this.hashCode();

            // 制造阻塞，观察异步情况 —— 默认异步执行，下一个任务不会被当前阻塞影响
            try {
                TimeUnit.SECONDS.sleep(2*INTERVAL_SECOND);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
