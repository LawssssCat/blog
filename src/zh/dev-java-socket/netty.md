---
title: Netty 介绍
order: 66
---


netty版本：
3.x、4.x、4.x

应用领域：

1. 分布式进程通信
如：hadoop、dubbo、akka等具有分布式功能的框架，底层RPC通信都是基于netty实现的，这些框架版本通常都还在用netty3.x（2019年2月10日）
2. 游戏服务器开发
最新的游戏服务器都有部分公司可能已经开始采用 netty4.x 或者 netty5.x

## 参考

+ netty rpc 实现 https://www.bilibili.com/video/av44457831/ \
  资料 https://www.jianshu.com/p/b0343bfd216e
+ rpc 介绍 https://www.jianshu.com/p/b0343bfd216e

## Demo

### ~~基本使用：客户端、服务端（3.10.5.Final）~~

#### 依赖

```xml
<dependencies>
  <!-- https://mvnrepository.com/artifact/io.netty/netty -->
  <dependency>
    <groupId>io.netty</groupId>
    <artifactId>netty</artifactId>
    <version>3.10.5.Final</version>
  </dependency>
</dependencies>
```

#### Server

::: tabs

@tab 入口

```java
package cn.edut.server;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class Server {

  public static void main(String[] args) {
    // 服务类
    ServerBootstrap bootstrap = new ServerBootstrap();

    ExecutorService bossExecutor = Executors.newCachedThreadPool();
    ExecutorService workerExecutor = Executors.newCachedThreadPool();

    // 设置niosocket工厂
    bootstrap.setFactory(new NioServerSocketChannelFactory(bossExecutor, workerExecutor));

    // 设置管道的工厂
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      // 管道可以理解为:一堆过滤器
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        // 通常服务类都是xxx+s,如channels
        ChannelPipeline pipeline = Channels.pipeline();
        // 添加handler
        pipeline.addLast("helloHandler", new HelloHandler());
        return pipeline;
      }
    });

    // 为服务类绑定端口
    bootstrap.bind(new InetSocketAddress(10101));

    System.out.println("服务启动!");
  }
}

```

@tab 处理器

```java
package cn.edut.server;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HelloHandler extends SimpleChannelHandler {

  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    System.out.println("MessageEvent:"+e);
    System.out.println("messageReceived");
    super.messageReceived(ctx, e);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    System.out.println("exceptionCaught");
    super.exceptionCaught(ctx, e);
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("channelConnected");
    super.channelConnected(ctx, e);
  }

  @Override
  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("channelDisconnected");
    super.channelDisconnected(ctx, e);
  }

  @Override
  public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("channelClosed");
    super.channelClosed(ctx, e);
  }

}
```

> **`channelClosed` 和 `channelDisconnected` 的区别：**
>
> + `channelClosed` ： channel 关闭的时候就触发
> + `channelDisconnected` ：==必须是连接已经建立==，关闭通道的时候才会触发

@tab 解码器

手写解码器

```java
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    System.out.println("messageReceived");
    System.out.println("MessageEvent:"+e);

    //获取客户端信息
    ChannelBuffer buffer = (ChannelBuffer) e.getMessage();
    System.out.println("message:"+new String(buffer.array()));

    super.messageReceived(ctx, e);
  }
```

一般不手写，而是使用内置StringDecoder、StringEncoder处理器类

```java
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    System.out.println("messageReceived");
    System.out.println("MessageEvent:" + e);

    // 获取客户端信息
    String message = (String) e.getMessage();
    System.out.println("message:" + message);

    // // 回写数据
    // ChannelBuffer copiedBuffer = ChannelBuffers.copiedBuffer("hi from server".getBytes());
    // ctx.getChannel().write(copiedBuffer);

    super.messageReceived(ctx, e);
  }
```

:::

#### Client

::: tabs

@tab 入口

```java
package cn.edut.client;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

public class Client {

  public static void main(String[] args) {
    // 服务类
    ClientBootstrap bootstrap = new ClientBootstrap();

    // 线程池
    ExecutorService bossExecutor = Executors.newCachedThreadPool();
    ExecutorService workerExecutor = Executors.newCachedThreadPool();

    // socket工厂
    bootstrap.setFactory(new NioClientSocketChannelFactory(bossExecutor, workerExecutor));

    // 管道工厂
    bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
      @Override
      public ChannelPipeline getPipeline() throws Exception {
        ChannelPipeline pipeline = Channels.pipeline();
        pipeline.addLast("decoder", new StringDecoder());
        pipeline.addLast("encoder", new StringEncoder());
        pipeline.addLast("hiHandler", new HiHandler());
        return pipeline;
      }
    });

    // 连接服务端
    ChannelFuture connect = bootstrap.connect(new InetSocketAddress("127.0.0.1",10101));
    Channel channel = connect.getChannel();

    System.out.println("client start");

    Scanner scanner = new Scanner(System.in);
    while(true) {
      System.out.println("请输入:");
      channel.write(scanner.nextLine());
    }
  }

}
```

@tab 处理器

```java
package cn.edut.client;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class HiHandler extends SimpleChannelHandler {
  @Override
  public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
    System.out.println("messageReceived");
    System.out.println("MessageEvent:" + e);

    // 获取服务端信息
    String message = (String) e.getMessage();
    System.out.println("message:" + message);
    super.messageReceived(ctx, e);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
    System.out.println("exceptionCaught");
    super.exceptionCaught(ctx, e);
  }

  @Override
  public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("channelConnected");
    super.channelConnected(ctx, e);
  }

  @Override
  public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("channelDisconnected");
    super.channelDisconnected(ctx, e);
  }

  @Override
  public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
    System.out.println("channelClosed");
    super.channelClosed(ctx, e);
  }
}
```

:::

### 基本使用：服务端

<RepoLink path="/code/demo-java-thread/demo-01-nio-netty" />

+ io.netty.channel.nio.NioEventLoopGroup —— 线程池

todo `public static AttributeKey<HttpSession> SESSION_KEY = AttributeKey.valueOf("session");`

todo ChannelInboundHandlerAdapter

## 网络握手

TCP/IP 建立连接的三个阶段被称为“三次握手”，三次握手完成后，客户端和服务端建立连接，开始传输数据。

+ A：`SYN` —— 客户端发起同步请求。你好呀，听说你开启了监听端口，我想给你传数据，方便建立连接吗，我先发一个序列号 `x` ？
+ B：`SYN + ACK` —— 服务端发起同步请求。是的呀，我给你发一个我的序列号 `y`，对了，你的下一个序列号是 `x+1` 吗？
+ A：`ACK` —— 客户端确认请求，对着呢，你的下一个序列号 `y+1` 吗？

这三轮同步和确认操作后，客户端和服务器端就建立起了稳定的连接。

![DEMO_3_SYN_ACK](https://files.catbox.moe/9ifpvg.png)

这个过程中，netty服务器内核会用到两个队列：

+ sync queue: 未完成三次握手的连接，内核正等待连接建立，记作 A 队列；
+ accept queue: 已完成三次握手，内核正等待进程执行 accept 函数的连接，记作 B 队列。

`ChannelOption.SO_BACKLOG` 配置就是控制 A + B 队列总长度的参数，如果这两个队列都满了，那么 Netty 服务将不会再接收新的连接请求了。
