package org.example.guava.concurrent.future;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CompletableFutureTest {
    @Test
    void test() {
        ExecutorService executor = Executors.newWorkStealingPool();
        CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
            log.info("sleep");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("sleep finish");
            return 10;
        }, executor); // 可以传线程池，否则使用 ForkJoin 线程池

        future.whenComplete((v, t) -> {
            log.info("wait finish, value:{}, throw:{}", v, t);
        }); // 相对回调线程同步
        future.whenCompleteAsync((v, t) -> {
            log.info("wait finish, value:{}, throw:{} (async)", v, t);
        }); // 相对回调线程异步

        // 主动 wait
        log.debug("--- wait finish ---");
        int time = 0;
        while (!future.isDone()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                log.debug("--- wait finish {} ---", time++);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.debug("--- wait finish ok ---");
    }
}
