package com.example.toolserverhttpcounter.utils;

import com.example.toolserverhttpcounter.common.TestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CompletableFutureExpandUtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(CompletableFutureExpandUtilsTest.class);

    @Test
    public void test() {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            LOGGER.info("---- start");
            TestUtils.sleep(100, TimeUnit.SECONDS);
            LOGGER.info("---- ok");
        });
        LOGGER.info("stage 1");
        long start = System.currentTimeMillis();
        LOGGER.info("stage 2");
        CompletableFutureExpandUtils.orTimeout(future, 0, TimeUnit.SECONDS);
        Throwable cause = null;
        try {
            future.get();
        } catch (ExecutionException | InterruptedException e) {
            LOGGER.error("some exception", e);
            cause = e.getCause() == null ? e : e.getCause();
        }
        Assertions.assertInstanceOf(TimeoutException.class, cause);
        LOGGER.info("stage 3");
        long end = System.currentTimeMillis();
        long diff = end - start;
        Assertions.assertTrue(diff < 1 * 1000L, "diff=" + diff);
        LOGGER.info("stage end");
    }
}