package org.example.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.*;
import java.util.stream.IntStream;

@Slf4j
public class CompletionServiceTest {
    final static ExecutorService pool = Executors.newFixedThreadPool(5);

    /**
     * 按照任务完成的先后顺序获取结果
     */
    @Test
    void test() {
        CompletionService<Integer> completionService = new ExecutorCompletionService<>(pool);

        IntStream.rangeClosed(0, 10).forEach(i -> completionService.submit((Callable<Integer>) () -> i));

        try {
            for (int i = 0; i < 10; i++) {
                // 💡 如果队列为空，take() 方法会阻塞，而 poll() 方法会返回 null
                Future<Integer> result = completionService.take(); // Blocking until a task is completed
                log.info("taskId: {}", result.get()); // Results are obtained in the order of completion
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
