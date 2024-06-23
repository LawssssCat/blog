package org.example.guava.other;

import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

@Slf4j
public class StopWatchTest {
    @Test
    void test() {
        process("ID0001");
    }

    void process(String orderId) {
      log.info("start process the order [{}]", orderId);
        Stopwatch stopwatch = Stopwatch.createStarted();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        log.info("The order [{}] process successful and elapsed [{}]", orderId, stopwatch.stop());
    }
}
