package org.example.guava.concurrent.ratelimit;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Slf4j
public class RateLimiterTest {
    private final RateLimiter limiter = RateLimiter.create(0.5); // 一秒 0.5 次 = 两秒 1 次
    @Test
    void test() {
        int num = 10;
        CountDownLatch countDownLatch = new CountDownLatch(num);
        // submit
        ExecutorService executorService = Executors.newWorkStealingPool();
        IntStream.range(0, 10).forEach(i -> {
            executorService.submit(() -> {
                // limiter.tryAcquire()
                log.info("waiting {}, limit require:{}", i, limiter.acquire());
                countDownLatch.countDown();
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
