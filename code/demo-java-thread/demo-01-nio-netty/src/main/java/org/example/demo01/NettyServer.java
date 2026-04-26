package org.example.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {
    private final int port;

    public NettyServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // [Use Thread Pools Wisely]
        // Netty uses EventLoopGroup as a thread pool.
        // You should carefully configure the number of threads in the EventLoopGroup based on the nature of your application.
        // For CPU-bound applications, you may want to limit the number of threads, while for I/O - bound applications, you can have more threads.
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // [Keep the ChannelPipeline Lean]
                        // The ChannelPipeline should not have too many ChannelHandlers.
                        // Each ChannelHandler adds some overhead, so you should only include the necessary handlers in the pipeline.
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // [Encoding and Decoding]
                            // In real-world applications, you often need to convert between different data formats.
                            // Netty provides ChannelHandlers for encoding and decoding data.
                            // For example, if you are working with JSON data, you can use a JsonObjectDecoder and JsonObjectEncoder.
                            // + StringDecoder/StringEncoder
                            // + JsonObjectDecoder/JsonObjectEncoder
                            // [Connection Management]
                            // Proper connection management is crucial. You should handle connection timeouts, idle connections, and gracefully close connections when they are no longer needed. Netty provides handlers like IdleStateHandler to detect idle connections.
                            // + IdleStateHandler
                            ch.pipeline().addLast(new SimpleServerHandler());
                        }
                    })
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        new NettyServer(port).run();
    }
}
