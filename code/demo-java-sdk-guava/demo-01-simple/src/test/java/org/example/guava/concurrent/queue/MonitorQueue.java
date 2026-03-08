package org.example.guava.concurrent.queue;

import com.google.common.util.concurrent.Monitor;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
class MonitorQueue implements Queue {
    private final LinkedList<Integer> queue = new LinkedList<>();
    private final int MAX = 10;

    private final Monitor monitor = new Monitor();
    private final Monitor.Guard CAN_OFFER = monitor.newGuard(() -> queue.size() < MAX);
    private final Monitor.Guard CAN_TAKE = monitor.newGuard(() -> !queue.isEmpty());

    @Override
    public void offer(int value) throws InterruptedException {
        log.debug("monitor queue add last: {}", value);
        try {
            monitor.enterWhen(CAN_OFFER);
            queue.add(value);
            log.debug("monitor queue add last: {} (ok)", value);
        } finally {
            monitor.leave();
        }
    }

    @Override
    public int take() throws InterruptedException {
        try {
            monitor.enterWhen(CAN_TAKE);
            Integer value = queue.removeFirst();
            log.debug("lock queue remove first: {}", value);
            return value;
        } finally {
            monitor.leave();
        }
    }
}
