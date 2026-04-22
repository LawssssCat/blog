package org.example.arthas;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

@Slf4j
public class NettyHttpTelnetBootstrapTest_01_netty_bind_fail {
    @Test
    public void test() throws ExecutionException, InterruptedException, TimeoutException {
        log.info("------------ start ------------");
        //负责接收客户端连接线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("server-boss", true));
        //负责处理客户端i/o事件、task任务、监听任务组
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("server-worker", true));
        ServerBootstrap boostrap = new ServerBootstrap();
        boostrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // 等待建立连接+等待消息处理分发的队列长度
                .option(ChannelOption.SO_BACKLOG, 1)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
//                        ch.pipeline().addLast(new ProtocolDetectHandler(channelGroup, handlerFactory, factory, workerGroup, httpSessionManager));
                    }
                });
        CompletableFuture<Object> fut = new CompletableFuture();
        boostrap.bind("255.255.255.255", 31808).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                Consumer<Throwable> doneHandler = (err) -> {
                    if (err == null) {
                        fut.complete((Object) null);
                    } else {
                        fut.completeExceptionally(err);
                    }
                };

                if (future.isSuccess()) {
                    log.info("bind: success");
                    doneHandler.accept(null);
                } else {
                    log.info("bind: fail");
                    doneHandler.accept(future.cause());
                }
            }
        });
        Assertions.assertThrowsExactly(ExecutionException.class, () -> {
            fut.get(6000, TimeUnit.MILLISECONDS); // 6s 内完成端口绑定，否则超时
        });
        log.info("------------ end ------------");
    }

}
