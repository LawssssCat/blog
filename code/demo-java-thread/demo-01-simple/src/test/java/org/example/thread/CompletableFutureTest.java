package org.example.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

@Slf4j
public class CompletableFutureTest {
    /**
     * 启动异步任务
     */
    @Test
    void test_supplyAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "Hello");

        future.thenApply(result -> {
            // Non-blocking callback to process the result
            System.out.println("Received result: " + result);
            return result.toUpperCase();
        });

        // Continue with other non-blocking operations
        log.info("----");
        future.join();
        log.info("----");
    }

    /**
     * 多任务编排
     */
    @Test
    void test_thenCompose() {
        CompletableFuture<String> firstTask = CompletableFuture.supplyAsync(() -> {
            // Simulate some computation
            return "First Task";
        });

        CompletableFuture<String> secondTask = CompletableFuture.supplyAsync(() -> {
            // Simulate some computation
            return "Second Task";
        });

        CompletableFuture<String> thirdTask = CompletableFuture.supplyAsync(() -> {
            // Simulate some computation
            return "Third Task";
        });

        // 使用thenCompose确保任务按照顺序完成
        CompletableFuture<String> result = firstTask.thenCompose(result1 ->
                secondTask.thenCompose(result2 ->
                        thirdTask.thenApply(result3 -> result1 + " -> " + result2 + " -> " + result3)
                )
        );

        // 异步获取结果
        result.thenAcceptAsync(log::info);

        // 阻塞等待所有任务完成
        log.info("---");
        result.join();
        log.info("---");
    }

    /**
     * 异常处理
     */
    @Test
    void test_exceptionally() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                    throw new RuntimeException();
                })
                .exceptionally(ex -> "errorFirstTask")
                .thenApply(firstTask -> firstTask + "secondTask")
                .thenApply(secondTask -> secondTask + "thirdTask")
                .thenApply(thirdTask -> thirdTask + "lastTask");
        future.join();
    }

    /**
     * 异常处理
     */
    @Test
    void test_handle() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "resultA")
                .thenApply(firstTask -> firstTask + "secondTask")
                .thenApply(secondTask -> {
                    throw new RuntimeException();
                })
                .handle(new BiFunction<Object, Throwable, Object>() {
                    @Override
                    public Object apply(Object re, Throwable throwable) {
                        if (throwable != null) {
                            return "errorThirdTask ";
                        }
                        return re;
                    }
                })
                .thenApply(thirdTask -> thirdTask + "lastTask");
        future.join();
    }
}
