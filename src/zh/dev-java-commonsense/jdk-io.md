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

> 参考
>
> + ~~[CSDN|说好不能打脸|架构设计：系统间通信（1）——概述从“聊天”开始上篇](https://blog.csdn.net/yinwenjie/article/details/48274255)~~
> + ~~[CSDN|说好不能打脸|架构设计：系统间通信（2）——概述从“聊天”开始下篇](https://blog.csdn.net/yinwenjie/article/details/48344989)~~
> + ~~[CSDN|说好不能打脸|架构设计：系统间通信（3）——IO通信模型和JAVA实践上篇](https://blog.csdn.net/yinwenjie/article/details/48472237)~~
> + [CSDN|说好不能打脸|架构设计：系统间通信（4）——IO通信模型和JAVA实践中篇](https://blog.csdn.net/yinwenjie/article/details/48522403)
> + ~~[CSDN|说好不能打脸|架构设计：系统间通信（5）——IO通信模型和JAVA实践下篇](https://blog.csdn.net/yinwenjie/article/details/48784375)~~
> + [CSDN|说好不能打脸|架构设计：系统间通信（6）——IO通信模型和Netty上篇](https://blog.csdn.net/yinwenjie/article/details/48829419)
> + [CSDN|说好不能打脸|架构设计：系统间通信（7）——IO通信模型和Netty下篇](https://blog.csdn.net/yinwenjie/article/details/48969853)

前置概念：

+ OSI七层模型
  + 应用层
  + 表示层
  + 会话层
  + 传输层
  + 网络层
  + 数据链路层
  + 物理层
+ TCP/IP四层模型（及其协议栈）
  + 应用层 - STMP/FTP/SSH/HTTP/...
  + 传输层 - TCP/UDP/...
  + 网络层 - IP/ICMP/...
  + 链路层 - Ethernet/Wi-Fi/PPP/...
+ 问题
  + 传输层
    + tcp
      1. 丢包
      1. Nagle 算法
      1. MTU / MSS 限制
      1. 发送缓冲区、接收缓冲区
      1. 滑动窗口、拥塞窗口控制
      1. 序号、重传
  + 应用层
    + udp作为传输层时
      1. 消息乱序
    + tcp作为传输层时
      1. 拆包、半包、粘包
        + 比喻
          + 40榴莲、20椰子，一车30。拆前30榴莲一车叫“拆包”（前30榴莲叫“半包（Half Packets）”），合并后10榴莲和20椰子一车叫“粘包（Sticky Packets）”
            ```java title="伪代码：粘包问题示例"
            /**
             * 服务器端（只负责接收消息）
            */
            class ServSocket {
                // 字节数组的长度
                private static final int BYTE_LENGTH = 20;  
                public static void main(String[] args) throws IOException {
                    // 创建 Socket 服务器
                    ServerSocket serverSocket = new ServerSocket(8888);
                    // 获取客户端连接
                    Socket clientSocket = serverSocket.accept();
                    // 得到客户端发送的流对象
                    try (InputStream inputStream = clientSocket.getInputStream()) {
                        while (true) {
                            // 循环获取客户端发送的信息
                            byte[] bytes = new byte[BYTE_LENGTH];
                            // 读取客户端发送的信息
                            int count = inputStream.read(bytes, 0, BYTE_LENGTH);
                            if (count > 0) {
                                // 成功接收到有效消息并打印
                                System.out.println("接收到客户端的信息是:" + new String(bytes));
                            }
                            count = 0;
                        }
                    }
                }
            }
            /**
             * 客户端（只负责发送消息）
            */
            static class ClientSocket {
                public static void main(String[] args) throws IOException {
                    // 创建 Socket 客户端并尝试连接服务器端
                    Socket socket = new Socket("127.0.0.1", 8888);
                    // 发送的消息内容
                    final String message = "Hi,Java."; 
                    // 使用输出流发送消息
                    try (OutputStream outputStream = socket.getOutputStream()) {
                        // 给服务器端发送 10 次消息
                        for (int i = 0; i < 10; i++) {
                            // 发送消息
                            outputStream.write(message.getBytes());
                        }
                    }
                }
            }
            ```
        + 根因
          + 因为TCP是面向连接的传输协议，TCP传输的数据是以流的形式，而流数据是没有明确的开始结尾边界，所以TCP也没办法判断哪一段流属于一个消息。而UDP是没有半包、粘包的问题，因为UPD是面向消息的，它有边界协议，可以根据消息的格式区分消息的开始和结尾，UDP和TCP两个发送消息就好像一个用桶运水，一个用水管运水，用水管运水的你是没办法区分那部分的水是属于哪一桶的。
        + 解决方案 <https://bbs.huaweicloud.com/blogs/377974>
          1. 长度边界（Fixed Length） —— 服务端和客户端规定固定长度的缓冲区，当消息数据长度不足时，使用规定的填充字符进行填充。弊端：增加了不必要的数据传输，造成网络传输负担，不建议使用。
          1. 符号边界（Delimiter Based） —— 在包体尾部增加标识符表示一条完整的消息数据已经结束。弊端：若消息体本身包含该标识符需要做转义处理，因此效率依然不高。
          1. 组合边界/长度字段首部（Length Field Based） —— 将包体分为消息头+消息体，消息头中信息为消息体的长度，接收方通过该长度信息读取后面指定长度的内容，需要注意的是需限制可能的最大长度从而规定长度占用字节数。该方法为处理粘包半包问题的常用方法。
            ```java title="伪代码：组合边界方案示例"
            // ================
            // 发送端：
            // ================
            //将发送的内容转化为字节数据
            byte[] bytes = Encoding.Default.GetBytes(content);
            //消息体长度
            Int16 length = (Int16)bytes.Length;
            //消息头长度
            byte[] lengthBytes = BitConverter.GetBytes(length);
            //发送的包体 = 消息头 + 消息体
            byte[] sendBytes = lengthBytes.Concat(bytes).ToArray();
            //发送
            socket.Send(sendBytes);
            // ================
            // 接收端
            // ================
            // [part:1]
            // 接收数据缓冲区
            byte[] readBuffer = new byte[1024];
            // 接收缓冲区的数据长度
            int bufferCount = 0; // bufferCount用于记录缓冲区中的有效数据长度，BeginReceive从缓冲区bufferCount的位置开始写入，缓冲区长度为1024，那么可写入的剩余量为1024 - bufferCount
            // [part:2]
            socket.BeginReceive(readBuffer,             //接收缓冲区
                    bufferCount,            //开始位置
                    1024 - bufferCount,     //最多读取的数据长度
                    0,                      //标志位
                    ReceiveCallback,        //接收数据回调函数
                    socket);
            // [part:3] 在收到新数据后，需要在回调函数中更新bufferCount，以便在下次接收数据时，写入到缓冲区中有效数据的后面。
            Socket socket = (Socket)ar.AsyncState;
            //接收数据的长度
            int count = socket.EndReceive(ar);
            bufferCount += count;
            // [part:4] 因为使用了Int16表示消息长度，所以缓冲区中至少有2个字节以上的数据时才去读取并处理，如果小于2，不足以解析出长度信息，如果大于2但小于消息长度+2，表示不足以读取到完整消息。
            if (bufferCount <= 2) return;
            Int16 length = BitConverter.ToInt16(readBuffer, 0);
            if (bufferCount < length + 2) return;
            //代码执行到此处表示已经有完整的消息，进行处理
            string content = Encoding.UTF8.GetString(readBuffer, 2, length);
            // [part:5] 完整消息读取后，将缓冲区的后续数据向前移位，更新缓冲区。
            int startIndex = 2 + length;
            int count = bufferCount - startIndex;
            Array.Copy(readBuffer, startIndex, readBuffer, 0, count);
            bufferCount -= startIndex;
            ```

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

目前流程的多路复用IO实现主要包括四种：select、poll、epoll、kqueue。下表是他们的一些重要特性的比较：

IO模型 | 相对性能 | 关键思路 | 操作系统 | JAVA支持情况
------ | ------ | ------ | ------ | ------
select | 较高 | Reactor | windows/Linux | 支持，Reactor模式(反应器设计模式)。Linux操作系统的 kernels 2.4内核版本之前，默认使用select；而目前windows下对同步IO的支持，都是select模型
poll | 较高 | Reactor | Linux | Linux下的JAVA NIO框架，Linux kernels 2.6内核版本之前使用poll进行支持。也是使用的Reactor模式
epoll | 高 | Reactor/Proactor | Linux | Linux kernels 2.6内核版本及以后使用epoll进行支持；Linux kernels 2.6内核版本之前使用poll进行支持；另外一定注意，由于Linux下没有Windows下的IOCP技术提供真正的异步IO 支持，所以Linux下使用epoll模拟异步IO
kqueue | 高 | Proactor | Linux | 目前JAVA的版本不支持

![nio 流程](https://files.catbox.moe/mikdxp)

概念：

+ Channel（通道）
  + **ServerSocketChannel**：应用服务器程序的监听通道。只有通过这个通道，应用程序才能向操作系统注册支持“多路复用IO”的端口监听。同时支持UDP协议和TCP协议。
  + **SocketChannel**：TCP Socket套接字的监听通道，一个Socket套接字对应了一个客户端IP：端口 到 服务器IP：端口的通信连接。
  + **DatagramChannel**：UDP 数据报文的监听通道。
+ Buffer（缓存）
  + **position**：缓存区目前这在操作的数据块位置
  + **limit**：缓存区最大可以进行操作的位置。缓存区的读写状态正式由这个属性控制的。
  + **capacity**：缓存区的最大容量。这个容量是在缓存区创建时进行指定的。
+ Selector（选择器）
  + Java实现
    + Selector —— 定义
    + AbstractSelector —— 由SelectorProvider提供
    + SelectorImpl —— 由SelectorProviderImpl提供
      + WindowSelectorImpl —— 由WindowSelectorProvider提供
      + EPollSelectorImpl —— 由EPollSelectorProvider提供
      + PollSelectorImpl —— 由PollSelectorProvider提供
      + DevPollSelectorImpl —— 由DevPollSelectorProvider提供

#### + 多路复用（JAVA原生）

通过NIO方式，减少任务调度的线程

+ `ServerSocketChannel`：对应传统 ServerSocket
+ `SocketChannel`：对应 Socket
+ `Selector`：nio 核心，用于监听 SocketChannel 和 ServerSocketChannel

```java
package testNSocket;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLDecoder;
import java.net.URLEncoder;

import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.BasicConfigurator;

public class SocketServer2 {
    static {
        BasicConfigurator.configure();
    }

    /**
     * 日志
     */
    private static final Log LOGGER = LogFactory.getLog(SocketServer2.class);

    /**
     * 改进的java nio server的代码中，由于buffer的大小设置的比较小。
     * 我们不再把一个client通过socket channel多次传给服务器的信息保存在beff中了（因为根本存不下）<br>
     * 我们使用socketchanel的hashcode作为key（当然您也可以自己确定一个id），信息的stringbuffer作为value，存储到服务器端的一个内存区域MESSAGEHASHCONTEXT。
     *
     * 如果您不清楚ConcurrentHashMap的作用和工作原理，请自行百度/Google
     */
    private static final ConcurrentMap<Integer, StringBuffer> MESSAGEHASHCONTEXT = new ConcurrentHashMap<Integer , StringBuffer>();

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket serverSocket = serverChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(83));

        Selector selector = Selector.open();
        //注意、服务器通道只能注册SelectionKey.OP_ACCEPT事件
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        try {
            while(true) {
                //如果条件成立，说明本次询问selector，并没有获取到任何准备好的、感兴趣的事件
                //java程序对多路复用IO的支持也包括了阻塞模式 和非阻塞模式两种。
                if(selector.select(100) == 0) {
                    //================================================
                    //      这里视业务情况，可以做一些然并卵的事情
                    //================================================
                    continue;
                }
                //这里就是本次询问操作系统，所获取到的“所关心的事件”的事件类型（每一个通道都是独立的）
                Iterator<SelectionKey> selecionKeys = selector.selectedKeys().iterator();

                while(selecionKeys.hasNext()) {
                    SelectionKey readyKey = selecionKeys.next();
                    //这个已经处理的readyKey一定要移除。如果不移除，就会一直存在在selector.selectedKeys集合中
                    //待到下一次selector.select() > 0时，这个readyKey又会被处理一次
                    selecionKeys.remove();

                    SelectableChannel selectableChannel = readyKey.channel();
                    if(readyKey.isValid() && readyKey.isAcceptable()) {
                        SocketServer2.LOGGER.info("======channel通道已经准备好=======");
                        /*
                         * 当server socket channel通道已经准备好，就可以从server socket channel中获取socketchannel了
                         * 拿到socket channel后，要做的事情就是马上到selector注册这个socket channel感兴趣的事情。
                         * 否则无法监听到这个socket channel到达的数据
                         * */
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel)selectableChannel;
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        registerSocketChannel(socketChannel , selector);

                    } else if(readyKey.isValid() && readyKey.isConnectable()) {
                        SocketServer2.LOGGER.info("======socket channel 建立连接=======");
                    } else if(readyKey.isValid() && readyKey.isReadable()) {
                        SocketServer2.LOGGER.info("======socket channel 数据准备完成，可以去读==读取=======");
                        readSocketChannel(readyKey);
                    }
                }
            }
        } catch(Exception e) {
            SocketServer2.LOGGER.error(e.getMessage() , e);
        } finally {
            serverSocket.close();
        }
    }

    /**
     * 在server socket channel接收到/准备好 一个新的 TCP连接后。
     * 就会向程序返回一个新的socketChannel。<br>
     * 但是这个新的socket channel并没有在selector“选择器/代理器”中注册，
     * 所以程序还没法通过selector通知这个socket channel的事件。
     * 于是我们拿到新的socket channel后，要做的第一个事情就是到selector“选择器/代理器”中注册这个
     * socket channel感兴趣的事件
     * @param socketChannel 新的socket channel
     * @param selector selector“选择器/代理器”
     * @throws Exception
     */
    private static void registerSocketChannel(SocketChannel socketChannel , Selector selector) throws Exception {
        socketChannel.configureBlocking(false);
        //socket通道可以且只可以注册三种事件SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT
        //最后一个参数视为 为这个socketchanne分配的缓存区
        socketChannel.register(selector, SelectionKey.OP_READ , ByteBuffer.allocate(50));
    }

    /**
     * 这个方法用于读取从客户端传来的信息。
     * 并且观察从客户端过来的socket channel在经过多次传输后，是否完成传输。
     * 如果传输完成，则返回一个true的标记。
     * @param socketChannel
     * @throws Exception
     */
    private static void readSocketChannel(SelectionKey readyKey) throws Exception {
        SocketChannel clientSocketChannel = (SocketChannel)readyKey.channel();
        //获取客户端使用的端口
        InetSocketAddress sourceSocketAddress = (InetSocketAddress)clientSocketChannel.getRemoteAddress();
        Integer resoucePort = sourceSocketAddress.getPort();

        //拿到这个socket channel使用的缓存区，准备读取数据
        //在后文，将详细讲解缓存区的用法概念，实际上重要的就是三个元素capacity,position和limit。
        ByteBuffer contextBytes = (ByteBuffer)readyKey.attachment();
        //将通道的数据写入到缓存区，注意是写入到缓存区。
        //这次，为了演示buff的使用方式，我们故意缩小了buff的容量大小到50byte，
        //以便演示channel对buff的多次读写操作
        int realLen = 0;
        StringBuffer message = new StringBuffer();
        //这句话的意思是，将目前通道中的数据写入到缓存区
        //最大可写入的数据量就是buff的容量
        while((realLen = clientSocketChannel.read(contextBytes)) != 0) {

            //一定要把buffer切换成“读”模式，否则由于limit = capacity
            //在read没有写满的情况下，就会导致多读
            contextBytes.flip();
            int position = contextBytes.position();
            int capacity = contextBytes.capacity();
            byte[] messageBytes = new byte[capacity];
            contextBytes.get(messageBytes, position, realLen);

            //这种方式也是可以读取数据的，而且不用关心position的位置。
            //因为是目前contextBytes所有的数据全部转出为一个byte数组。
            //使用这种方式时，一定要自己控制好读取的最终位置（realLen很重要）
            //byte[] messageBytes = contextBytes.array();

            //注意中文乱码的问题，我个人喜好是使用URLDecoder/URLEncoder，进行解编码。
            //当然java nio框架本身也提供编解码方式，看个人咯
            String messageEncode = new String(messageBytes , 0 , realLen , "UTF-8");
            message.append(messageEncode);

            //再切换成“写”模式，直接情况缓存的方式，最快捷
            contextBytes.clear();
        }

        //如果发现本次接收的信息中有over关键字，说明信息接收完了
        if(URLDecoder.decode(message.toString(), "UTF-8").indexOf("over") != -1) {
            //则从messageHashContext中，取出之前已经收到的信息，组合成完整的信息
            Integer channelUUID = clientSocketChannel.hashCode();
            SocketServer2.LOGGER.info("端口:" + resoucePort + "客户端发来的信息======message : " + message);
            StringBuffer completeMessage;
            //清空MESSAGEHASHCONTEXT中的历史记录
            StringBuffer historyMessage = MESSAGEHASHCONTEXT.remove(channelUUID);
            if(historyMessage == null) {
                completeMessage = message;
            } else {
                completeMessage = historyMessage.append(message);
            }
            SocketServer2.LOGGER.info("端口:" + resoucePort + "客户端发来的完整信息======completeMessage : " + URLDecoder.decode(completeMessage.toString(), "UTF-8"));

            //======================================================
            //          当然接受完成后，可以在这里正式处理业务了
            //======================================================

            //回发数据，并关闭channel
            ByteBuffer sendBuffer = ByteBuffer.wrap(URLEncoder.encode("回发处理结果", "UTF-8").getBytes());
            clientSocketChannel.write(sendBuffer);
            clientSocketChannel.close();
        } else {
            //如果没有发现有“over”关键字，说明还没有接受完，则将本次接受到的信息存入messageHashContext
            SocketServer2.LOGGER.info("端口:" + resoucePort + "客户端信息还未接受完，继续接受======message : " + URLDecoder.decode(message.toString(), "UTF-8"));
            //每一个channel对象都是独立的，所以可以使用对象的hash值，作为唯一标示
            Integer channelUUID = clientSocketChannel.hashCode();

            //然后获取这个channel下以前已经达到的message信息
            StringBuffer historyMessage = MESSAGEHASHCONTEXT.get(channelUUID);
            if(historyMessage == null) {
                historyMessage = new StringBuffer();
                MESSAGEHASHCONTEXT.put(channelUUID, historyMessage.append(message));
            }
        }
    }
}
```

#### + Netty

[link](./jdk-io-nio-netty.md)
