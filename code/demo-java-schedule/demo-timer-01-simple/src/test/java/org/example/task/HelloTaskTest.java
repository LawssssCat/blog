package org.example.task;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HelloTaskTest {
    private static final int NUM = 2;
    private static final CountDownLatch countDownLatch = new CountDownLatch(NUM*2);
    private static final List<String> expectArray = Collections.synchronizedList(new ArrayList<>());
    @Test
    void test() {
        Timer timer = new Timer(); // 底层使用小顶堆实现任务轮询
        for (int i = 0; i < 2; i++) {
            log.info("启动 task {}", i);
            HelloTask task = HelloTask.builder()
                    .num(i)
                    .build();
            Calendar cal = Calendar.getInstance();
            Date firstTime = cal.getTime(); // 开始时间
            long period = 2000L; // 时间间隔 ❗计划的时间间隔，但实际执行时间会受到上次执行时间影响。比如上次任务未执行结束，则下次任务即便到达执行时间，但仍然等待上次任务执行后才执行
            timer.schedule(task, firstTime, period);
        }
        try {
            log.info("主进程等待");
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("主进程结束");
        Assertions.assertArrayEquals(new String[] {"0", "1", "0", "1"}, expectArray.toArray());
    }

    @Builder
    private static class HelloTask extends TimerTask {
        private static final String PREFIX = "taskHello";
        private int num;
        private String logo() {
            return PREFIX + "-" + num;
        }
        @Override
        public void run() {
            int i = 0;
            while (i++<3) {
                log.info("{} {} {} start", logo(), i, Thread.currentThread().hashCode());
                try {
                    TimeUnit.MILLISECONDS.sleep(1000); // 💡任务间隔 2s 执行一次，但这里等待 1*3s，这会导致下次任务执行延时（观察 log 打印的时间）
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("{} {} {} end", logo(), i, Thread.currentThread().hashCode());
            }
            expectArray.add(String.valueOf(num));
            countDownLatch.countDown();
        }
    }
}
