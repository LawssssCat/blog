package com.example.toolserverhttpcounter.common;

import java.util.concurrent.TimeUnit;

public class TestUtils {
    public static void sleep(long num, TimeUnit timeUnit) {
        try {
            timeUnit.sleep(num);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
