# demo-01-springboot-server-simple

参考：

+ <https://springdoc.cn/spring-boot-websocket/>

websocket是h5中提供的在单个tcp连接上进行全双工通信的协议。

## 协议

### RFC6455： WebSocket API

+ 标准：
RFC6455定义了websocket的通信标准。
+ 实现：
  + java —— JSR356定义了websocket在java中的api。
  + tomcat —— web容器，如tomcat的`tomcat-websocket.jar`中实现了上述接口。

```xml
<dependency>
    <groupId>javax.websocket</groupId>
    <artifactId>javax.websocket-api</artifactId>
    <version>1.1</version>
    <scope>provided</scope>
</dependency>
```

## STOMP（Simple Text Oriented Messaging Protocol，面向文本的简单消息协议）

定义：

+ **Simple（简单）** —— 设计简单，易于实现。命令（如 CONNECT、SUBSCRIBE、SEND）易懂。
+ **Text Oriented（面向文本）** —— 底层基于文本（String）传输，而不是复杂的二进制字节流。使得它适合在 Web 浏览器和后端服务器之间（通过 WebSocket）进行数据交互。
+ **Messaging Protocol（消息协议）** —— 是一种标准的消息队列协议，专门为了实现异步消息传递和发布/订阅（Publish/Subscribe）模式而设计。

概念：

+ **连接地址（Connection Endpoint）** —— 在服务端（如 Spring）通过配置类暴露出一个标准的 HTTP 握手端点。

  ```text
  CONNECT
  accept-version:1.1,1.2
  heart-beat:10000,10000
  
  ^@
  ```
  (注：`^@` 代表 Null 字节，是 STOMP 帧的结束符)

  + 交互过程
    1. 客户端先通过传统的 TCP/HTTP 拨通这个 URL（例如 ws://localhost:8080/ws-endpoint）。
    2. 握手成功并升级为 WebSocket 管道
    3. 管道升级后，客户端立马发送 STOMP 的 CONNECT 帧 激活 STOMP 子协议。

+ **消息广播通道（Destination/Subscription）** —— 客户端发送 SUBSCRIBE 命令，并在 destination 头部指定要监听的通道路径。

  ```text
  SUBSCRIBE
  id:sub-01
  destination:/topic/greetings
  
  ^@
  ```
  （解释：客户端告诉服务器，“我要订阅 /topic/greetings 这个通道，在系统里给我编个号叫 sub-01”）。

  + 路由规则（Spring 规范,在 Spring 的标准实现中，通过前缀来区分“广播”和“私聊”）：
    + `/topic/*`（广播）：服务端的消息代理（Broker）看到这个前缀，就知道是一对多。只要有消息往这里发，所有订阅了这个路径的客户端都会收到。
    + `/queue/*`（点对点/私聊）：通常用于一对一。服务端会确保消息只投递给特定的某一个用户。

+ **业务处理入口（Message Sending）** —— 通过 SEND 帧 和 在 destination 头部的应用目的地前缀（Application Destination Prefixes） 来定义业务入口。

  ```text
  SEND
  destination:/app/hello
  content-type:text/plain
  
  Hello Server, please process this!^@
  ```

  + 路由规则（Spring 规范）：
    + `/app/*`：这是关键。Spring 服务端配置了 `config.setApplicationDestinationPrefixes("/app")`。
      当客户端发送的目的地是以 /app 开头（如 /app/hello）时，消息不会直接进广播队列，而是被拦截并路由到后端带有 `@MessageMapping("/hello")` 注解的 Spring Controller 方法 中，交由 Java 业务代码处理。
