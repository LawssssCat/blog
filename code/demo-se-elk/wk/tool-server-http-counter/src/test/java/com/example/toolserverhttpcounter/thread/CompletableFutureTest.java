package com.example.toolserverhttpcounter.thread;

import com.example.toolserverhttpcounter.common.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureTest.class);

    @Test
    void test() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            LOGGER.info("---- start");
            TestUtils.sleep(1, TimeUnit.SECONDS);
            LOGGER.info("---- ok");
        });
        LOGGER.info("stage 1");
        long start = System.currentTimeMillis();
        Throwable cause = null;
        try {
            future.get(0, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            LOGGER.error("some exception", e);
            cause = e.getCause() == null ? e : e.getCause();
        }
        Assertions.assertInstanceOf(TimeoutException.class, cause);
        LOGGER.info("stage 2");
        long end = System.currentTimeMillis();
        long diff = end - start;
        Assertions.assertTrue(diff < 1 * 1000L, "diff=" + diff);
        LOGGER.info("stage end");

    }
}
