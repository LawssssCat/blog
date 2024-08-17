package org.example.thread;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

@Slf4j
public class ForkJoinPoolTest {
    @Test
    void teste() {
        long start = 1L;
        long end = 100000L;
        // ForkJoinPool forkJoinPool = new ForkJoinPool(3); // 最大线程数
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool(); // 一般用公共的线程池即可
        long invoke = forkJoinPool.invoke(new ForKJoinCalculate(start, end)); // invoke 同步等待结果
        long expect = LongStream.rangeClosed(start, end).sum();
        Assertions.assertEquals(expect, invoke);
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
                log.debug("calc: {} -> {}", start, end);
                long sum = 0l;
                for (long i = start; i<=end; i++) {
                    sum += i;
                }
                return sum;
            } else {
                // 分治
                long mid = (start + end) / 2;
                ForkJoinTask<Long> left = new ForKJoinCalculate(start, mid).fork();
                ForkJoinTask<Long> right = new ForKJoinCalculate(mid + 1, end).fork();
                return left.join() + right.join();
            }
        }
    }
}
