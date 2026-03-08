package org.example.guava.concurrent.future;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.MoreExecutors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ListeningExecutorTest {
    @Test
    void test() {
        ExecutorService executor = Executors.newWorkStealingPool();
        ListenableFuture<Integer> future = MoreExecutors.listeningDecorator(executor).submit(() -> {
            log.info("sleep");
            TimeUnit.SECONDS.sleep(1);
            log.info("sleep finish");
            return 10;
        });
        future.addListener(() -> {
            log.info("wait finish, value:{}");
        }, executor); // 非阻塞，当 future 完成后，通过线程池线程调用回调方法
        Futures.addCallback(future, new FutureCallback<Integer>() { // 非阻塞，且获取返回值
            @Override
            public void onSuccess(@Nullable Integer result) {
                log.info("wait finish, value:{}", result);
            }

            @Override
            public void onFailure(Throwable t) {
                log.error("oh shit", t);
            }
        }, executor); // 可传线程池，否则默认 DirectExecutor （用回调线程执行回调）

        // 主动 wait
        log.debug("--- wait finish ---");
        int time = 0;
        while (!future.isDone()) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
                log.debug("--- wait finish {} ---", time++);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        log.debug("--- wait finish ok ---");
    }
}
