package org.example.job;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.quartz.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SimpleJobStateTest extends AbstractSimpleJobTest {
    private static final String KEY_COUNTER = "counter";
    @Test
    void testPersist() throws SchedulerException {
        Map<String, Object> map = new HashMap<>(); // 存储数据
        startSchedule(PersistJob.class, map);
        Assertions.assertEquals(REPEAT+1, map.get(KEY_COUNTER));
    }

    @Test
    void testTransient() throws SchedulerException {
        Map<String, Object> map = new HashMap<>(); // 存储数据
        startSchedule(TransientJob.class, map);
        Assertions.assertEquals(1, map.get(KEY_COUNTER));
    }

    private void startSchedule(Class<? extends Job> clazz, Map<String, Object> map) throws SchedulerException {
        startSchedule(clazz,
                jobBuilder -> {
                    JobDataMap jobDataMap = new JobDataMap();
                    jobDataMap.put(KEY_COUNTER, map);
                    jobBuilder.usingJobData(jobDataMap);
                },
                triggerTriggerBuilder -> {
                    triggerTriggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).withRepeatCount(REPEAT));
                }
        );
    }

    /**
     * 有状态
     */
    @PersistJobDataAfterExecution // 持久化 JobDataMap 数据
    @DisallowConcurrentExecution // 通常同 @PersistJobDataAfterExecution 一起使用，避免当同一个 job（JobDetail） 的两个实例被并发执行时，由于竞争导致 JobDataMap 中存储的数据不确定的
    public static class PersistJob extends TransientJob {}

    /**
     * 无状态
     */
    @Getter
    @Setter
    public static class TransientJob implements Job {
        private String logo = "TransientJob";
        private int count = 0;
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            log.info("{} {} context: count={}, 本次执行={}, 下次执行={}",
                    this.logo,
                    this.hashCode() + // 不同
                            "/" + context.hashCode() + // 不同
                            "/" + context.getJobDetail().hashCode() + // ❗相同
                            "/" + context.getTrigger().hashCode() + // ❗相同
                            "/" + context.getJobDetail().getJobDataMap().hashCode() + // ❗相同
                            "/" + context.getTrigger().getJobDataMap().hashCode() // ❗相同
                    ,
                    ++count,
                    context.getFireTime(),
                    context.getNextFireTime());
            context.getJobDetail().getJobDataMap().put("count", count); // 💡光改属性值还不行，还要手动插入到 Map 中，否则不被持久化
            Map<String, Object> map = (Map<String, Object>) context.getJobDetail().getJobDataMap().get(KEY_COUNTER);
            map.put(KEY_COUNTER, count);
        }
    }
}
