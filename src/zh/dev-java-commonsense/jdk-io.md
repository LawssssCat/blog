---
title: JDK I/O （Input and Output）
---

## IO类型

+ BIO（Blocking I/O，传统阻塞型I/O） —— 线程阻塞读写数据，直到操作完成，中途无法干别的事情。
  + 概念：
    + 流（Stream） —— 数据存取的源头
  + 场景：
    + 文件读写 —— `File`
    + 网络编程（低并发） —— `ServerSocket` / `Socket`
  + 接口：
    + `InputStream` / `OutputStream` —— 字节（byte）流。用于从文件或网络按字节读取二进制数据（如图片、音频、视频）。
    + `Reader` / `Writer` —— 字符（char）流。用于处理字符编码（如UTF8），避免乱码。
    + `PipedInputStream` / `PipedOutputStream` —— 管道流。用于在同一个JVM中的两个不同线程间通信
    + `ZipInputStream` / `ZipOutputStream` —— 压缩流
+ NIO（Non-blocking I/O，非阻塞I/O） —— 核心是多路复用（Multiplexing）
  + 概念：
    + 缓冲区（Buffer） —— 数据的容器。实际是划分一块内存，通过 position/limit/capacity 指针管理数据在该内存上的读写
    + 通道（Channel） —— 数据的传送带 `SocketChannel` / `ServerSocketChannel`
    + 选择器（Selector） —— 多路复用（Multiplexing）的指挥官，通过轮询可以管理成千上万个 Channel 上的事件（如连接、读、写），极大地减少了线程切换的开销
  + 场景：
    + 文件读写（大文件）
    + 网络编程（高并发）
  + 接口：
    + `ByteBuffer` —— 字节缓冲区。本质是一个内存数组，提供通过接口控制 position/limit/capacity 指针完成数据读写。
    + `FileChannel` —— 文件通道。连接到文件的纽带，类似传统流，但是双向的（可读、可写），支持高效批量数据传输和文件锁定。
    + `MappedByteBuffer` —— 内存映射文件缓冲区。把磁盘文件直接映射到操作系统的虚拟内存中，读写这个Buffer等于直接读写该磁盘文件，绕过JVM的堆内存拷贝，即零拷贝。适合处理GB级别的超大文件。
+ AIO（Asynchronous I/O，异步非阻塞I/O） —— 在NIO的基础上，提供的一套新的文件读写接口，实现“异步”读写操作
  + 概念：
    + 定时轮询 —— 定时抢占部分处理器时间检查是否是否完成 `Future`
    + 方法回调 —— 在任务完成或失败时触发处理方式回调 `CompletionHandler`
  + 场景：
    + 文件读写 —— `AsynchronousFileChannel`
    + ~~网络编程 —— `AsynchronousSocketChannel` / `AsynchronousServerSocketChannel`~~ （工程实践上，该场景主要使用NIO应付高并发需求）
  + 接口：
    + `Paths` —— 更现代的文件系统路径操作，屏蔽操作系统的差别
    + `Files` —— 更现代的文件操作，封装常用的文件操作
    + `WatchService` —— 文件监控服务。利用操作系统的通知机制，实现实时监控文件/文件夹的变化，一旦发生创建、修改、删除等事件马上触发回调函数。常用于配置文件的热加载。

## 场景：文件读写

todo 略

todo Jar包

```bash
CodeSource codeSource = ArthasBootstrap.class.getProtectionDomain().getCodeSource();
# file:/mnt/c/my/learning-java/arthas/arthas-packaging-3.6.8-bin/arthas-core.jar
codeSource.getLocation().toURI()
# /mnt/c/my/learning-java/arthas/arthas-packaging-3.6.8-bin/arthas-core.jar
codeSource.getLocation().toURI().getSchemeSpecificPart()
```

### 封装：commons-io

todo FileSystemUtils

todo FileMonitor

#### + Stream Fork —— 参考 <https://www.tutorialspoint.com/commons_io/commons_io_teeinputstream.htm>

具体功能有

+ 将 in 流转为 out 流
+ 将一个 in 流分流多个 in 流

```java
<!-- @include: @project/code/demo-java-common/demo-io/src/test/java/org/example/commonio/TeeIOStreamTest.java -->
```

## 场景：网络编程

### BIO

#### + 单线程

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

#### + 多线程/线程池

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

### NIO

todo IO 多路复用 select/poll/epoll
todo `strace -ff -o ./out java BIOSocket.Java`
todo [Doug Lea](https://gee.cs.oswego.edu/) | Scalable IO in Java <https://gee.cs.oswego.edu/dl/cpjslides/nio.pdf>

todo Java NIO原理 图文分析及代码实现
<https://www.iteye.com/blog/weixiaolu-1479656>

#### + 多路复用

通过NIO方式，减少任务调度的线程

+ `ServerSocketChannel`：对应传统 ServerSocket
+ `SocketChannel`：对应 Socket
+ `Selector`：nio 核心，用于监听 SocketChannel 和 ServerSocketChannel

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

#### + Netty

[link](./jdk-io-nio-netty.md)
