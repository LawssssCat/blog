package org.example.guava.concurrent.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FutureTest {
    @Test
    void test() {
        Future<Integer> future = Executors.newWorkStealingPool().submit(() -> {
            log.info("sleep");
            TimeUnit.SECONDS.sleep(1);
            log.info("sleep finish");
            return 10;
        });
        try {
            log.info("wait");
            Integer value = future.get();// 阻塞
            log.info("wait finish, value:{}", value);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
