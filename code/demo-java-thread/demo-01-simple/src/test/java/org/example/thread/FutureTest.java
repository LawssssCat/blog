package org.example.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class FutureTest {
    /**
     * 直接调用
     */
    @Test
    void test01() {
        FutureTask<Integer> future = new FutureTask<>((Callable<Integer>) () -> 5);
        new Thread(future).start();
        // ... 执行其他任务，不会阻塞
        try {
            log.info("taskId: {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    final static ExecutorService executorService = Executors.newFixedThreadPool(2);

    /**
     * 线程池调用
     */
    @Test
    void test02() {
        Future<Integer> future = executorService.submit((Callable<Integer>) () -> 42);
        // ... 执行其他任务，不会阻塞
        try {
            log.info("taskId: {}", future.get());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量任务调用
     */
    @Test
    void test03() {
        // 准备任务
        List<Callable<Integer>> tasks = IntStream.rangeClosed(0,10)
                .mapToObj(i -> (Callable<Integer>) () -> i)
                .collect(Collectors.toList());

        // 批量发布任务 （invoke 会阻塞，想要非阻塞使用 map.submit）
        // List<Future<Integer>> results = executorService.invokeAll(tasks);
        List<Future<Integer>> results = tasks.stream().map(executorService::submit).collect(Collectors.toList());

        // ... 执行其他任务，不会阻塞

        results.forEach(result -> {
            try {
                log.info("taskId: {}", result.get()); // Results are obtained in the order of task submission
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
