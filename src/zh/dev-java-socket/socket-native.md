---
title: Java Socket 原生 介绍
order: 66
---

## BIO

### 单线程

问题：
服务端接收到数据之后，继续阻塞。
单线程阻塞情况，无法接收到其他客户端响应。

:::::: tabs

@tab 服务端

```java
package test.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class OioServer {
  public static void main(String[] args) throws IOException {
    // 创建 Socket 服务, 监听10101端口
    ServerSocket server = new ServerSocket(10101);
    System.out.println("服务器启动!");
    while (true) {
      // 获取一个套接字(阻塞1)
      final Socket socket = server.accept();
      System.out.println("来了一个新客户端!");
      // 业务处理
      handler(socket);
    }
  }

  /**
   * 读取数据
   */
  private static void handler(Socket socket) {
    try {
      byte[] bytes = new byte[1024];
      InputStream in = socket.getInputStream() ;
      while (true) {
        // 读取数据(阻塞2)
        int read = in.read(bytes);
        if (read != -1) {
          System.out.println(new String(bytes, 0, read));
        }else {
          break ;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        System.out.println("socket关闭");
        socket.close();
      }catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
```

@tab 客户端

::: tip
windows开启：程序和功能/启动或关闭 Windows 功能/Telnet 客户端

```bash
# 用法
telnet -h
```

:::

```bash
telnet 127.0.0.1 10101
# 组合键 `ctrl+]` ，进入命令行模式，发送行数据
Microsoft Telnet> send hello
```

::::::

### 多线程/线程池

为了同时响应 多个客户端，可我们单线程情况有两个阻塞点，需要多线程

新问题：
每一个客户端请求，都创建一个线程，资源浪费。\
导致结果：
无法做长连接，只能做短连接（如 tomcat，底层使用的就是 socket）

```java
package test.nio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OioServer {

  public static void main(String[] args) throws IOException {
    ExecutorService pool = Executors.newCachedThreadPool();
    // 创建 Socket 服务, 监听10101端口
    ServerSocket server = new ServerSocket(10101);
    System.out.println("服务器启动!");
    while (true) {
      // 获取一个套接字(阻塞1)
      final Socket socket = server.accept();
      System.out.println("来了一个新客户端!");
      pool.execute(new Runnable() {
        @Override
        public void run() {
          // 业务处理
          handler(socket);
        }
      });
    }
  }

  /**
   * 读取数据
   */
  private static void handler(Socket socket) {
    try {
      byte[] bytes = new byte[1024];
      InputStream in = socket.getInputStream();
      while (true) {
        // 读取数据(阻塞2)
        int read = in.read(bytes);
        if (read != -1) {
          System.out.println(new String(bytes, 0, read));
        } else {
          break;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        System.out.println("socket关闭");
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
```

## NIO

通过NIO方式，减少任务调度的线程

+ `ServerSocketChannel `：对应传统 ServerSocket
+ `SocketChannel `：对应 Socket
+ `Selector`：nio 核心，用于监听 SocketChannel 和 ServerSocketChannel

> todo Java NIO原理 图文分析及代码实现
https://www.iteye.com/blog/weixiaolu-1479656


```java
package test.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO服务器
 */
public class NioServer {
  // 通道管理器
  private Selector selector;

  /**
   * 启动服务端测试
   *
   * @throws IOException
   */
  public static void main(String[] args) throws IOException {
    NioServer server = new NioServer();
    server.initServer(8000);
    server.listen();
  }

  /**
   * 获得一个 ServerSocket 通道, 并且对该通道做一些初始化的工作
   *
   * @param port 绑定的端口号
   * @throws IOException
   */
  public void initServer(int port) throws IOException {
    // 获得一个ServerSocket通道
    ServerSocketChannel socketChannelServer = ServerSocketChannel.open();
    // 设置通道为非阻塞
    socketChannelServer.configureBlocking(false);
    // 将该通道对应的 SeverSocket 绑定到 port 端口
    socketChannelServer.socket().bind(new InetSocketAddress(port));
    // 获得一个通道管理器
    this.selector = Selector.open();
    // 将通道管理器和该通道绑定, 并且为该通道注册 SelectionKey.OP_ACCEPT 事件
    // 注册该事件后, 当该事件到达时, selector.select() 会返回
    // 如果该事件没有到达, selector.select() 会一直阻塞
    socketChannelServer.register(selector, SelectionKey.OP_ACCEPT);
  }

  /**
   * 采用轮询的方式监听 selector 上是否有需要处理的事件。 如果有,则进行处理
   */
  public void listen() throws IOException {
    System.out.println("服务端启动成功!");
    // 轮询访问 selector
    while (true) {
      // 当注册的事件到达时, 方法返回; 否则, 该方法会一直阻塞
      this.selector.select();
      // 获得 selector 中选中的项的迭代器, 选中的项为注册的事件
      Iterator<SelectionKey> iterator = this.selector.selectedKeys().iterator();
      while (iterator.hasNext()) {
        SelectionKey key = iterator.next();
        // 删除已经选中的 key, 以防重复处理
        iterator.remove();
        handler(key);
      }
    }
  }

  /**
   * 处理请求
   *
   * @param key selector 轮询出的被触发事件
   * @throws IOException
   */
  private void handler(SelectionKey key) throws IOException {
    if (key.isAcceptable()) {
      // 客户端请求连接事件 OP_ACCEPT
      handlerAccept(key);
    } else if (key.isReadable()) {
      // 获得了可读的事件 OP_READ
      handlerRead(key);
    }
  }

  /**
   * 处理连接请求
   *
   * @param key OP_ACCEPT 事件
   * @throws IOException
   */
  private void handlerAccept(SelectionKey key) throws IOException {
    ServerSocketChannel socketChannelServer = (ServerSocketChannel) key.channel();
    // 获得和客户端连接的通道
    SocketChannel channel = socketChannelServer.accept();
    // 设置成非阻塞
    channel.configureBlocking(false);

    // 在这里可以给客户端发送信息哦
    System.out.println("新的客户端连接");
    // 在和客户端连接成功后, 为了可以接收到客户端的信息, 需要给通道设置可读的权限
    channel.register(this.selector, SelectionKey.OP_READ);
  }

  /**
   * 处理读的事件
   *
   * @param key OP_READ
   * @throws IOException
   */
  private void handlerRead(SelectionKey key) throws IOException {
    // 服务器可读信息:得到事件发生的Socket通道
    SocketChannel channel = (SocketChannel) key.channel();
    // 创建读取的缓冲区
    ByteBuffer buffer = ByteBuffer.allocate(10); // bytes
    channel.read(buffer);
    byte[] data = buffer.array();
    String msg = new String(data);
    System.out.println("服务端收到信息:" + msg);

    // 回写数据
    ByteBuffer outBuffer = ByteBuffer.wrap("好的".getBytes());
    // 将消息回送给客户端
    channel.write(outBuffer);
  }

}
```

关于 NIO 方式，有 netty 封装。 [link_netty](./netty.md)
