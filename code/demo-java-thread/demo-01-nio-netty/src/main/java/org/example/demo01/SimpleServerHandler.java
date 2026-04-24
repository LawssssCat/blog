package org.example.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String request = in.toString(StandardCharsets.UTF_8);
        System.out.println("Received from client: " + request);

        // [Reuse ByteBuf]
        // ByteBuf creation can be expensive.
        // You should reuse ByteBuf instances as much as possible to improve performance.
        // Netty provides ByteBufAllocator to manage ByteBuf allocation and reuse.
        String response = "Hello, client! I received your message: " + request;
        ByteBuf resp = Unpooled.copiedBuffer(response, StandardCharsets.UTF_8);
        ctx.write(resp);
        ctx.flush();
    }

    // [Error Handling]
    // Error handling should be done at every stage of the ChannelPipeline.
    // In the exceptionCaught method of ChannelHandler, you can log the error and take appropriate actions such as closing the channel.
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}