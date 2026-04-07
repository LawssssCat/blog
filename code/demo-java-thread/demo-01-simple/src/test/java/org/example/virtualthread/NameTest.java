package org.example.virtualthread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

@Slf4j
public class NameTest {
    @Test
    public void name() throws InterruptedException {
        Runnable runnable = () -> {
            Thread thread = Thread.currentThread();
            log.info("platform: {}", thread.getName());
        };
        for (Thread thread : Arrays.asList(
                Thread.ofPlatform().start(runnable),
                Thread.ofVirtual().start(runnable)
        )) {
            thread.join();
        }
    }
}
