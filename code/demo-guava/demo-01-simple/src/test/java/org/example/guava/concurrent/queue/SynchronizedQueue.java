package org.example.guava.concurrent.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
class SynchronizedQueue implements Queue {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private final int MAX = 10;

    @Override
    public void offer(int value) throws InterruptedException {
        log.debug("sync queue add last: {}", value);
        synchronized (queue) {
            while (queue.size() >= MAX) {
                queue.wait();
            }
            queue.addLast(value);
            queue.notifyAll();
        }
        log.debug("sync queue add last: {} (ok)", value);
    }

    @Override
    public int take() throws InterruptedException {
        synchronized (queue) {
            while (queue.isEmpty()) {
                queue.wait();
            }
            Integer value = queue.removeFirst();
            log.debug("sync queue remove first: {}", value);
            queue.notifyAll();
            return value;
        }
    }
}
