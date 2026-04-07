package org.example.virtualthread;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;

@Slf4j
public class NameTest {
    @Test
    public void test_name() throws InterruptedException {
        Runnable runnable = () -> log.info("thread-info: {}", Thread.currentThread());
        for (Thread thread : Arrays.asList(
                // thread-info: Thread[#43,Thread-0,5,main]
                // threadId=43
                // threadName=Thread-0
                // threadPriority=5
                // threadGroup=main
                // @see java.lang.VirtualThread.toString
                Thread.ofPlatform().start(runnable),
                // thread-info: VirtualThread[#44]/runnable@ForkJoinPool-1-worker-1
                // threadId=44
                // threadName=
                // carrierState=runnable （carrier=载体）
                // carrierName=ForkJoinPool-1-worker-1
                // - threadPoolName=ForkJoinPool-1
                // - threadPoolWorker=worker-1
                // @see java.lang.VirtualThread.toString
                Thread.ofVirtual().start(runnable)
        )) {
            thread.join();
        }
    }

    /**
     * IO密集型、等待密集型
     */
    @Test
    public void test_steal() throws Exception {
        int n = 10000;
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        TestStealResult result_virtual = do_test_steal(n, Executors.newVirtualThreadPerTaskExecutor());
        TestStealResult result_steal = do_test_steal(n, Executors.newWorkStealingPool(availableProcessors * 100));
        TestStealResult result_platform = do_test_steal(n, Executors.newFixedThreadPool(n));
        TestStealResult result_pool = do_test_steal(n, new ThreadPoolExecutor(availableProcessors, n, 1, TimeUnit.DAYS, new SynchronousQueue<>()));
        log.info("========== show ==========");
        // 8核 = 16线程 => aps=16
        // available-processor-size:16
        log.info("available-processor-size:{}", availableProcessors);
        // virtual:TestStealResult[workerSize=16, workTime=1405] —— 线程最少，速度较慢 ==== 但由于工作线程数量太少，过分复用线程，导致steal开销增大，但无理论并行处理数量上限
        log.info("virtual:{}", result_virtual);
        // stealll:TestStealResult[workerSize=1600, workTime=831] —— 线程中间，速度较慢 ==== 合理复用线程，资源利用率高，且无理论并行处理数量上限
        log.info("stealll:{}", result_steal);
        // platfor:TestStealResult[workerSize=10000, workTime=1336] —— 线程最多，速度较慢 ==== 线程切换耗时
        log.info("platfor:{}", result_platform);
        // poollll:TestStealResult[workerSize=3086, workTime=653] —— 线程中间，速度最快 ==== 合理复用线程，资源利用率高，但有线程数量上限
        log.info("poollll:{}", result_pool);
    }

    public static record TestStealResult(int workerSize, long workTime) {}

    private TestStealResult do_test_steal(int n, ExecutorService factory) throws InterruptedException, ExecutionException {
        log.info("========== ready ==========");
        Object[] records = new Object[n];
        Object[] list = new Object[n];
        for (int i = 0; i < n; i++) {
            int index = i;
            list[index] = (Runnable) () -> {
                String start = getWorkerName(Thread.currentThread());
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String end = getWorkerName(Thread.currentThread());
                boolean isSame = StringUtils.equals(start, end);
                log.info("{}:{}:[{}][{}]",
                        "%4s".formatted(index),
                        "%5s".formatted(isSame),
                        start,
                        end);
                records[index] = Pair.of(start, end);
            };
        }
        Thread.sleep(100);
        log.info("========== start ==========");
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            Runnable runnable = (Runnable) list[i];
            list[i] = factory.submit(runnable);
        }
        for (int i = 0; i < n; i++) {
            Future future = (Future) list[i];
            future.get();
        }
        log.info("========== collect ==========");
        long endTime = System.currentTimeMillis();
        Set<String> usedThreadNameSet = new HashSet<>();
        for (int i = 0; i < n; i++) {
            Pair<String, String> record = (Pair<String, String>) records[i];
            usedThreadNameSet.add(record.getLeft());
            usedThreadNameSet.add(record.getRight());
        }
        TestStealResult testStealResult = new TestStealResult(
                usedThreadNameSet.size(),
                endTime - startTime
        );
        log.info("========== clean ==========");
        factory.shutdownNow();
        Thread.sleep(100);
        return testStealResult;
    }

    private String getWorkerName(Thread thread) {
        if (thread.isVirtual()) {
            String virtualThreadStr = thread.toString();
            String[] split = virtualThreadStr.split("@");
            return split[1];
        } else {
            return thread.getName();
        }
    }
}
