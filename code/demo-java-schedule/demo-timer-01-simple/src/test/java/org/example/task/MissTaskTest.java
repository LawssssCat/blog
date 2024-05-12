package org.example.task;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Slf4j
public class MissTaskTest {
    private static final int NUM = 10;
    private static final CountDownLatch countDownLatch = new CountDownLatch(NUM);
    @Test
    void test() {
        Timer timer = new Timer();
        for (int i = 0; i < NUM; i++) {
            log.info("主进程开始任务 {}", i);
            HelloTask task = HelloTask.builder().num(i).build();
            Date firstTime = Calendar.getInstance().getTime();
            long period = 100L;
            timer.schedule(task, firstTime, period);
        }
        try {
            log.info("主进程等待");
            countDownLatch.await(2L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("主进程结束 {}", countDownLatch.getCount());
        Assertions.assertTrue(countDownLatch.getCount()>0); // 给了足够时间，任务依然未全部执行 —— 任务丢失

    }
    @Builder
    private static class HelloTask extends TimerTask {
        private static final String PREFIX = "helloTask";
        private int num;
        private String logo() {
            return PREFIX + "-" + num;
        }
        @Override
        public void run() {
            log.info("{} 开始", logo());
            try {
                TimeUnit.MILLISECONDS.sleep(300L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("{} 结束", logo());
            countDownLatch.countDown();
        }
    }
}
