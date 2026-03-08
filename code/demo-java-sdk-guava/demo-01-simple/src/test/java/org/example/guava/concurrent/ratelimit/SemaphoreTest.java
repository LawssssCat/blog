package org.example.guava.concurrent.ratelimit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

@Slf4j
public class SemaphoreTest {
    private final Semaphore semaphore = new Semaphore(3); // 允许 3 并发
    @Test
    void test() {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        // submit
        ExecutorService executorService = Executors.newWorkStealingPool();
        IntStream.range(0, 10).forEach(i -> {
            executorService.submit(() -> {
                try {
                    semaphore.acquireUninterruptibly();
                    log.info("waiting {}", i);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    countDownLatch.countDown();
                } finally {
                    semaphore.release();
                }
            });
        });
        // shutdown
        executorService.shutdown();
        boolean wait = true;
        while (wait) {
            try {
                log.debug("--- wait ---");
                wait = !executorService.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // assert
        Assertions.assertEquals(0, countDownLatch.getCount());
    }
}
