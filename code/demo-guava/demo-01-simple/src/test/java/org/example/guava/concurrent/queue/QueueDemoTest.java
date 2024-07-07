package org.example.guava.concurrent.queue;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

@Slf4j
public class QueueDemoTest {
    private final static int NUM = 20;
    private final CountDownLatch counter = new CountDownLatch(NUM);

    /**
     * 队列（synchronized版本）
     */
    @Test
    void testSynchronizedQueue() {
        testQueue(new SynchronizedQueue());
    }

    /**
     * 队列（ReentrantLock版本）
     */
    @Test
    void testReentrantLockQueue() {
        testQueue(new ReentrantLockQueue());
    }

    /**
     * 队列（Monitor版本）
     */
    @Test
    void testMonitoryQueue() {
        testQueue(new MonitorQueue());
    }

    private void testQueue(Queue queue) {
        log.info("--- offer");
        Stream.iterate(0, n -> n+1).limit(NUM).forEach(n ->{
            new Thread(() -> {
                try {
                    queue.offer(n);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });
        log.info("--- take");
        Stream.iterate(0, n -> n+1).limit(NUM).parallel().forEach(n ->{
            counter.countDown();
            log.info("--- take2: {}-{}", n, counter.getCount());
            try {
                queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("--- assert");
        Assertions.assertEquals(0, counter.getCount());
    }
}
