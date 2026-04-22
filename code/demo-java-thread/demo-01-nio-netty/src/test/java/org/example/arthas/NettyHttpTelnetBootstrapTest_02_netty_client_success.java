package org.example.arthas;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

/**
 * 参考：
 * https://github.com/fujiangwei/netty-demo/blob/master/src/main/java/com/kinson/netty/client/ClientIniterHandler.java
 */
@Slf4j
public class NettyHttpTelnetBootstrapTest_02_netty_client_success {
    @Test
    public void test() throws ExecutionException, InterruptedException, TimeoutException, IOException {
        // 客户端数量
        // 同一时刻，服务端队列长度需要大于等于客户端连接数量，否则服务端拒绝连接“connect refuse”。
        int clientNum = 2;
        log.info("------------ start ------------");
        //负责接收客户端连接线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("server-boss", true));
        //负责处理客户端i/o事件、task任务、监听任务组
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("server-worker", true));
        ServerBootstrap boostrap = new ServerBootstrap();
        boostrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // BACKLOG用于构造服务端套接字ServerSocket对象
                // 负载 = 当前服务请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
                // 负载 = 等待建立连接+等待消息处理分发的队列长度
                .option(ChannelOption.SO_BACKLOG, clientNum)
                // 是否启用心跳保活机制：todo 机制具体流程
                .option(ChannelOption.SO_KEEPALIVE, true)
                // handler 发生在初始化的时候
                .handler(new LoggingHandler(LogLevel.INFO))
                // childHandler 发生在客户端连接之后
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("server: [{}] initChannel", ch.remoteAddress());
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new ServerHandler());
                    }
                });
        CompletableFuture<Object> fut = new CompletableFuture();
        ChannelFuture channelFuture = boostrap.bind("0.0.0.0", 31808).addListener(new GenericFutureListener<Future<? super Void>>() {
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
        // 等待监听建立
        // 方法1：
        Channel serverChannel = channelFuture.sync().channel();
        // 方法2：
        // fut.get(6000, TimeUnit.MILLISECONDS); // 6s 内完成端口绑定，否则超时
        // 监听服务器关闭
        // serverChannel.closeFuture().sync();
        serverChannel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                log.info("bind: close {}", future.isSuccess());
                // 清理服务器资源
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }
        });



        log.info("------------ client ------------");
        CompletableFuture[] clients = new CompletableFuture[clientNum];
        for (int x = 0; x < clientNum; x++) {
            final int xx = x;
            clients[x] = CompletableFuture.runAsync(() -> {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(new NioEventLoopGroup(new DefaultThreadFactory("client-" + xx)));
                bootstrap.channel(NioSocketChannel.class);
                // handler 发生在初始化的时候
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("client: initChannel");
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        pipeline.addLast("stringD", new StringDecoder());
                        pipeline.addLast("stringC", new StringEncoder());
//                        pipeline.addLast("http", new HttpClientCodec());
                        pipeline.addLast(new ClientHandler());
                    }
                });
                log.info("client: bootstrap ok");
                ChannelFuture connect = bootstrap.connect("127.0.0.1", 31808);
                log.info("client: connect ok");
                ChannelFuture sync = null;
                try {
                    sync = connect.sync();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                log.info("client: sync ok");
                Channel channel = sync.channel();
                for (int i = 0; i < 3; i++) {
                    // 向服务器发送内容
                    log.info("client: channel msg-{}", i);
                    channel.writeAndFlush("hello, msg-" + i);
                    ThreadUtils.sleepQuietly(Duration.ofSeconds(1));
                }
            });
        }
        log.info("------------ wait ------------");
        CompletableFuture.allOf(clients).join();
//        boostrap = null;
        log.info("------------ end ------------");
    }

    @Slf4j
    private static class ClientHandler extends ChannelInboundHandlerAdapter {
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            log.info("client: active");
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            log.info("client: receive \"{}\"", msg);
        }
    }

    @Slf4j
    private static class ServerHandler extends SimpleChannelInboundHandler<String> {
        /**
         * 所有活动的用户
         */
        public static final ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

        /**
         * 读消息通道
         */
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            Channel channel = ctx.channel();
            log.info("server: [{}] {}", channel.remoteAddress(), msg);
            // 回复
            for (Channel ch : group){
                if (ch == channel) {
                    ch.writeAndFlush("[you]: " + msg);
                } else {
                    ch.writeAndFlush("[" + channel.remoteAddress() + "]: " + msg);
                }
            }
        }

        /**
         * 处理新加的消息通道
         */
        @Override
        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            log.info("server: [{}] added", channel.remoteAddress());
            for (Channel ch : group) {
                ch.writeAndFlush("[" + channel.remoteAddress() + "] coming");
            }
            group.add(channel);
        }

        /**
         * 处理退出消息通道
         */
        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            log.info("server: [{}] removed", channel.remoteAddress());
            for (Channel ch : group) {
                if (ch != channel) {
                    ch.writeAndFlush("[" + channel.remoteAddress() + "] coming");
                }
            }
            boolean remove = group.remove(channel);
            log.info("server: [{}] group remove {}", channel.remoteAddress(), remove);
        }

        /**
         * 在建立连接时发送消息
         */
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            boolean active = channel.isActive();
            log.info("server: [{}] active {}", channel.remoteAddress(), active);
            ctx.writeAndFlush("[server]: welcome");
        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            Channel channel = ctx.channel();
            boolean active = channel.isActive();
            log.info("server: [{}] active {}", channel.remoteAddress(), active);
        }

        /**
         * 异常捕获
         */
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            Channel channel = ctx.channel();
            log.warn("server: [{}] leave the room with exception", channel.remoteAddress(), cause);
            // 管道异常，关闭管道上下文，释放内存
            ctx.close()
                    // 等待关闭完成
                    .sync();
            boolean remove = group.remove(channel);
            log.warn("server: [{}] group remove {}", channel.remoteAddress(), remove);
        }
    }
}
