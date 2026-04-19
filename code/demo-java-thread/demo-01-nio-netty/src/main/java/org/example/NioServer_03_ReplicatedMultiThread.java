package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LoggingHandler;

import java.io.IOException;

/**
 * 主从 Reactor 多线程模型：
 * - 主Reactor —— 只负责监听连接建立事件
 * - 从Reactor —— 只负责分发读写业务事件
 */
public class NioServer_03_ReplicatedMultiThread {
    private static int port = 8209;

    public static void main(String[] args) throws IOException, InterruptedException {
        // 启动器，负责组装netty组件、启动服务器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        // 设置线程组
        serverBootstrap.group(
                // Boss线程组
                new NioEventLoopGroup(),
                // Worker线程组
                new NioEventLoopGroup()
        );
        // 指定Channel类型
        serverBootstrap.channel(NioServerSocketChannel.class);
        // 设置子处理器
        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            // 和客户端进行数据读写的管道初始化
            @Override
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                System.out.println("客户端连接完成！");
                // 添加具体handler
                nioSocketChannel.pipeline().addLast(new LoggingHandler());
                nioSocketChannel.pipeline().addLast(new StringDecoder());
                nioSocketChannel.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        System.out.println("客户端读取数据:" + msg);
                    }
                });
            }
        });
        // 绑定端口
        ChannelFuture f = serverBootstrap.bind(port).sync();
    }
}
