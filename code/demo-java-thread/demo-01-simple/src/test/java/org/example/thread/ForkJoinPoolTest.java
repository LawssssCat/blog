package org.example.thread;

import com.google.common.base.Stopwatch;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.function.Supplier;
import java.util.stream.LongStream;

@Slf4j
public class ForkJoinPoolTest {
    @SneakyThrows
    @Test
    void teste() {
        long start = 1L;
        long end = 10000000000L;
        // ForkJoinPool forkJoinPool = new ForkJoinPool(3); // 最大线程数
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool(); // 一般用公共的线程池即可
        Future<Long> invoke = time("fork", () -> {
            return forkJoinPool.invoke(new ForKJoinCalculate(start, end)); // invoke 同步等待结果
        });
        Future<Long> expect = time("common", () -> {
            return LongStream.rangeClosed(start, end).sum();
        });
        Assertions.assertEquals(expect.get(), invoke.get());
    }

    private Future<Long> time(String key, Supplier<Long> func) {
        return CompletableFuture.supplyAsync(() -> {
            Stopwatch stopwatch = Stopwatch.createStarted();
            long result = func.get();
            stopwatch.stop();
            log.info("{}: {}", key, stopwatch.toString());
            return result;
        });
    }

    /**
     * 计算 ∑(n,m) 的结果
     */
    @AllArgsConstructor
    static class ForKJoinCalculate extends RecursiveTask<Long> {
        private long start;
        private long end;
        /**
         * 计算量少于该值，才开始计算结果；否则，Fork 出新任务，进行分治计算
         */
        private static final long THRESHOLD = 10000L;

        @Override
        protected Long compute() {
            if (end - start <= THRESHOLD) {
                // log.debug("calc: {} -> {}", start, end);
                long sum = 0l;
                for (long i = start; i <= end; i++) {
                    sum += i;
                }
                return sum;
            } else {
                // 分治
                long old = end;
                end = (start + end) / 2;
                // ForkJoinTask<Long> left = new ForKJoinCalculate(start, end).fork();
                ForkJoinTask<Long> right = new ForKJoinCalculate(end + 1, old).fork();
                // return left.join() + right.join(); // 方法一：中（不必要的创建对象）
                return compute() + right.join(); // 方法二：更快
            }
        }
    }
}
