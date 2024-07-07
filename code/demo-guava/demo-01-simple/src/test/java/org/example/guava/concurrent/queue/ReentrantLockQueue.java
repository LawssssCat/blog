package org.example.guava.concurrent.queue;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
class ReentrantLockQueue implements Queue {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private final int MAX = 10;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition FULL_CONDITION = lock.newCondition();
    private final Condition EMPTY_CONDITION = lock.newCondition();

    @Override
    public void offer(int value) throws InterruptedException {
        log.debug("lock queue add last: {}", value);
        try {
            lock.lock();
            while (queue.size() >= MAX) {
                FULL_CONDITION.await();
            }
            queue.addLast(value);
            EMPTY_CONDITION.signalAll();
            log.debug("lock queue add last: {} (ok)", value);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public int take() throws InterruptedException {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                EMPTY_CONDITION.await();
            }
            Integer value = queue.removeFirst();
            FULL_CONDITION.signalAll();
            log.debug("lock queue remove first: {}", value);
            return value;
        } finally {
            lock.unlock();
        }
    }
}
