package org.example.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.MessageFormat;
import java.util.Date;
import java.util.concurrent.*;
import java.util.function.Function;

/**
 * 模拟 schedule 捕获线程异常失效的情况
 */
@Slf4j
public class ScheduleExecutorServiceCaughtExceptionFailTest {
    /**
     * 捕获异常失效：异常日志 “exception in ...” 没有打印
     */
    @Test
    void testSchedule_FailCaughtException_01() {
        newAndCaughtExceptionAndSchedule(threadFactory -> {
            return new ScheduledThreadPoolExecutor(1, threadFactory);
        });
    }

    /**
     * 捕获异常情况： 异常日志 “exception in ...” 没有打印
     */
    @Test
    void testSchedule_FailCaughtException_02() {
       newAndCaughtExceptionAndSchedule(threadFactory -> {
           return Executors.newSingleThreadScheduledExecutor(threadFactory);
       });
    }

    private static void newAndCaughtExceptionAndSchedule(Function<ThreadFactory, ScheduledExecutorService> func) {
        Thread.UncaughtExceptionHandler uncaughtExceptionHandler = new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.warn(MessageFormat.format("exception in {0}", t.getName()), e); // 💡不走到这行
            }
        };
        ThreadFactory factory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                log.debug("new thread!");
                Thread thread = new Thread(r);
                thread.setUncaughtExceptionHandler(uncaughtExceptionHandler); // 💡配置有效，但功能没有被触发，应为该功能只有在线程池 execute 方法异常时才触发
                return thread;
            }
        };
        ScheduledExecutorService executorService = func.apply(factory);
        // 💡schedule 调用线程池 submit 方法，而非线程池 execute 方法
        ScheduledFuture<?> scheduledFuture = executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                log.info("---- {}", new Date());
                try {
                    int a = 1 / 0;
                } catch (RuntimeException e) {
                    log.warn("throw: {}", e.getMessage());
                    throw e;
                }
            }
        }, 0, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
