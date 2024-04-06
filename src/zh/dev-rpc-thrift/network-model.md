---
title: Thrift 网络模型 (java)
date: 2024-04-06
category:
  - thrift
order: 4
---

从驱动形式上，Thrift 提供的网络服务模型分为：单线程、多线程、时间驱动。

从另一个角度分为：阻塞服务模型、非阻塞服务模型。

- 阻塞服务模型： TSimpleServer, TThreadPoolServer
- 非阻塞服务模型： TNonblockingServer, THsHaServer, TThreadedSelectorServer

下面以 Java 开发的角度分析各种网络服务模型的使用场景、性能情况。

<!-- more -->

```class Thrift 网络模型
class TServer {
  serve()
  stop()
  isServing()
}
TServer <|-- AbstractNonblockingServer
TServer <|-- TSimpleServer
TServer <|-- TSaslNonblockingServer
TServer <|-- TThreadPoolServer

AbstractNonblockingServer <|-- TNonblockingServer
AbstractNonblockingServer <|-- TThreadedSelectorServer

TNonblockingServer <|-- THsHaServer
```

## TSimpleServer

TSimpleServer 的工作模式采用最简单的阻塞 IO。其实现方法简洁明了，便于理解，但是一次只能接收和处理一个 Socket 连接，效率比较低。
它主要用于演示 Thrift 的工作过程，在实际开发工程中很少用到。

```flow:ant
st=>start: 开始
e=>end: 结束
op1=>operation: 启动监听 Socket
cond=>condition: 是否停止
op2=>operation: accept 一个业务 socket
sub1=>subroutine: 完成这个业务的 rpc 调用请求处理

st->op1->cond
cond(yes, right)->e
cond(no, bottom)->op2
op2(bottom)->sub1(left)->op1
```

::: tabs

@tab 服务端

```java title="SimpleService.java in server"
<!-- @include: @project/code/demo-thrift/thrift-server/src/main/java/org/example/service/SimpleService.java -->
```

@tab 客户端

```java title="SimpleClient.java in client"
<!-- @include: @project/code/demo-thrift/thrift-client/src/main/java/org/example/client/SimpleClient.java -->
```

:::

## TThreadPoolServer

TThreadPoolServer 模式采用阻塞 Socket 方式工作，主线程负责阻塞式监听是否有新 Socket 到来，具体的业务处理交由一个线程池来处理。
（类似 nio）

```flow:ant
st=>start: 开始
e=>end: 结束
op1=>operation: 启动监听 Socket
cond=>condition: 是否停止
op2=>operation: accept 一个业务 socket
pal1=>parallel: 通知线程池完成请求
pal2=>parallel: 等待处理任务
sub1=>subroutine: 完成这个业务的 rpc 调用请求处理

st->op1->cond
cond(yes, right)->e
cond(no, bottom)->op2
op2(bottom)->pal1(path1,left)->op1
pal1(path2, bottom)->pal2
pal2(path3, bottom)->sub1(left)->pal2
```

**优点**

拆分了监听线程（Accept Thread）和处理客户端连接的工作线程（Worker Thread）。数据读取和业务处理都交给线程池处理。因此在存在并发时新连接也能够被及时接受。
（线程池模式比较适合用在服务器端能够预知客户端并发量的情况下，这时每个请求都能被业务线程池及时处理）

**缺点**

线程池模式的处理能力受限于线程池的工作能力，当并发请求数量大于线程池中的线程数量时，新请求也只能排队等待。
另外，默认线程池允许创建的最大线程数量为 `Intger.MAX_VALUE`，这可能会创建出大量线程，导致 OOM（内存溢出） 风险。

## TNonblockingServer

TNonblockingServer 模式也是单线程工作，但是采用 NIO 模式，==利用 IO 多路复用模型处理 Socket 就绪事件==，对于有数据来到的 Socket 进行数据读取操作，对于有数据发送的 Socket 则进行数据发送操作，对于监听 Socket 则产生一个新业务 Socket 并将其注册到 Selector 上。

```flow:ant
st=>start: 开始
ed=>end: 结束

op1=>operation: Selector 监听 Socket
cond=>condition: 是否停止
op2=>operation: 监听 Socket 就绪
cond2=>condition: 任务完成
op3=>operation: 取出就绪 Socket
cond21(align-next=no)=>condition: 需要 accept
opt21=>subroutine: Selector 监听 accept Socket
cond22(align-next=no)=>condition: 需要 read
opt22=>subroutine: 读取数据，处理业务
cond23(align-next=no)=>condition: 需要 write
opt23=>subroutine: 写入操作
errlog=>subroutine: 错误日志

st->op1->cond(yes,right)->ed
cond(no,bottom)->op2(bottom)->cond2(yes,left)->op1
cond2(no, bottom)->op3
op3->cond21(yes,right)->opt21(right)->op2
cond21(no)->cond22(yes,right)->opt22(right)->op2
cond22(no)->cond23(yes,right)->opt23(right)->op2
cond23(no,bottom)->errlog
```

::: note
TNonblockingServer 要求底层的传输通道必须使用 TFramedTransport。
:::

::: tabs

@tab 服务端

```java title="NonblockingService.java in server"
<!-- @include: @project/code/demo-thrift/thrift-server/src/main/java/org/example/service/NonblockingService.java -->
```

@tab 客户端

```java title="NonblockingClient.java in client"
<!-- @include: @project/code/demo-thrift/thrift-client/src/main/java/org/example/client/NonblockingClient.java -->
```

:::

**优点**

相比 TSimpleServer 效率提升主要体现在 IO 多路复用上，TNonblockingServer 采用非阻塞 IO，对 accept/read/write 等 IO 事件进行监控和处理，同时监控多个 Socket 状态的变化。

**缺点**

TNonblockingServer 模式在业务处理上还是采用单线程顺序来完成。在业务处理比较复杂、耗时的时候效率不高，例如某些接口函数需要读取数据库执行时间较长，会导致整个服务器被阻塞。

## THsHaServer

鉴于 TNonblockingServer 的缺点，THsHaServer 继承于 TNonblockingServer，引入了线程池提高了任务处理的并发能力。
（Reactor 模型）

::: note
THsHaServer 和 TNonblockingServer 一样，要求底层的传输通道必须使用 TFramedTransport。
:::

> 其他都一样，就是读 Socket 时的处理后面加了线程池。

```flow:ant
cond22(align-next=no)=>condition: 需要 read
opt22=>operation: 读取数据
sub=>subroutine: 处理业务（线程池）

cond22(yes,right)->opt22(right)->sub
```

**优点**

THsHaServer 于 TNonblockingServer 模式相比，THsHaServer 在完成数据读取之后，将业务处理过程交给线程池处理，主线程直接返回进行下一个循环操作，效率大大提升。

**缺点**

主线程仍然需要完成所有 Socket 的监听接收、数据读取、数据写入操作。当并发请求数较大时，且发送数据量较多时，监听 Socket 上新连接请求不能被及时接受。

## TThreadedSelectorServer

TThreadedSelectorServer 是对 THsHaServer 的一种补充。它将 Selector 中的读写 IO 事件（read/wirete）从主线程中分离出来。同时引入 Worker 工作线程池。

TThreadedSelectorServer 模式是目前 Thrift 提供的最高级的线程服务模型，它内部有如下几个部分构成：

1. 一个 AcceptThread 专门用于处理监听 Socket 上新的连接。
1. 若干个 SelectorThread 专门用于处理业务 Socket 的网络 I/O 读写操作，所有网络数据的读写均是由这些线程来完成。
1. 一个负载均衡器 SelectorThreadLoadBalancer 对象，主要用于 AcceptThread 线程接收一个新的 Socket 连接请求时，决定将这个新连接请求分配给哪个 SelectorThread 线程处理。

> 多个 Selector 的处理就很像 Tomcat7 中的处理。但 Tomcat8 中认为多个 Selector 对新能提升不大，所以在以后版本中又改回由一个 Selector 处理全部 IO。
