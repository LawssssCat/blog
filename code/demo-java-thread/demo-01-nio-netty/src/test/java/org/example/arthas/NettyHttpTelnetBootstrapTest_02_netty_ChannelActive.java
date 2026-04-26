package org.example.arthas;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
public class NettyHttpTelnetBootstrapTest_02_netty_ChannelActive {
    @Test
    public void test() throws InterruptedException {
        log.info("------------ start ------------");
        //负责接收客户端连接线程
        EventLoopGroup bossGroup = new NioEventLoopGroup(new DefaultThreadFactory("server-boss", true));
        //负责处理客户端i/o事件、task任务、监听任务组
        EventLoopGroup workerGroup = new NioEventLoopGroup(new DefaultThreadFactory("server-worker", true));
        ServerBootstrap boostrap = new ServerBootstrap();
        boostrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                // handler 发生在初始化的时候
                .handler(new LoggingHandler(LogLevel.INFO))
                // childHandler 发生在客户端连接之后
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("server: [{}] initChannel", ch);
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                log.info("server: [{}] channelActive {}", ctx.channel(), ctx.channel() == ch); // true
                                // todo
//                                ctx.pipeline().remove(channelInitializer);
                                ctx.channel().eventLoop().schedule(() -> {
                                    log.info("server: [{}] eventLoop schedule", ctx.channel());
                                    ChannelInboundHandlerAdapter me = this;
                                    ctx.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            log.info("server: [{}] channelActive 2", ctx.channel());
                                            // todo 2
                                        }
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                            log.info("server: [{}] channelRead 2 \"{}\"", ctx.channel(), msg);
                                            // todo 2
                                        }
                                    });
                                    ctx.pipeline().remove(me);
                                    ctx.fireChannelActive();
                                }, 1000, TimeUnit.MILLISECONDS);
//                                super.channelActive(ctx);
                            }
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("server: [{}] channelRead \"{}\"", ctx.channel(), msg);
                                // todo
//                                super.channelRead(ctx, msg); // next handle
//                                ctx.fireChannelRead("xxx:" + msg);
                            }
                        });
                    }
                });
        boostrap.bind("0.0.0.0", 31822).sync().channel();

        log.info("------------ client ------------");
        int clientNum = 2;
        CompletableFuture[] clients = new CompletableFuture[clientNum];
        for (int i = 0; i < clientNum; i++) {
            String clientName = "client-" + i;
            clients[i] = CompletableFuture.runAsync(() -> {
                Bootstrap bootstrap = new Bootstrap();
                bootstrap.group(new NioEventLoopGroup(new DefaultThreadFactory(clientName)));
                bootstrap.channel(NioSocketChannel.class);
                bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        log.info("client: initChannel");
                        // todo
                    }
                });
                log.info("client: bootstrap ok");
                ChannelFuture connect = bootstrap.connect("127.0.0.1", 31822);
                log.info("client: connect ok");
                try {
                    Channel channel = connect.sync().channel();
                    // todo
                    log.info("client: channel wait");
                    ThreadUtils.sleepQuietly(Duration.ofSeconds(3));
                    ByteBuf msg = Unpooled.copiedBuffer("hello, " + clientName, StandardCharsets.UTF_8);
                    channel.writeAndFlush(msg);
                    ThreadUtils.sleepQuietly(Duration.ofSeconds(1));
                    log.info("client: channel ignore");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        log.info("------------ wait ------------");
        CompletableFuture.allOf(clients).join();
        log.info("------------ end ------------");
    }
}
