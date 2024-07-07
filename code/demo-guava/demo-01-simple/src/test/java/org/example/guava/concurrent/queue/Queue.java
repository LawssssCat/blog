package org.example.guava.concurrent.queue;

interface Queue {
    void offer(int value) throws InterruptedException;
    int take() throws InterruptedException;
}
