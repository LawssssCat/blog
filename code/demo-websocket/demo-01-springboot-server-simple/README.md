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


---

todo 分布式服务端管理

```bash
#########
# [窗口A]
#########
redis-cli -h 127.0.0.1 -p 6379
# 输入订阅命令（频道名与你 Java 里的常量一致）
SUBSCRIBE websocket-bridge
#########
# [窗口B]
#########
redis-cli -h 127.0.0.1 -p 6379
# 向指定频道发送测试文本
PUBLISH websocket-bridge "Hello, Redis Pub/Sub Test!"

#########################
如果功能正常，
窗口 B 会返回一个整数 (integer) 1（代表当前有 1 个客户端成功收到了这则广播）；
同时 窗口 A 也会瞬间刷出如下日志：
1) "message"
2) "websocket-bridge"
3) "Hello, Redis Pub/Sub Test!"
#########################
如果功能被禁用：
虽然 Redis 默认是开启 Pub/Sub 的，但为了提高生产环境的安全性，
很多运维人员或云服务商（如 AWS ElastiCache、阿里云）会通过以下两种官方手段将该功能强行禁用：
+ 禁用手段1：在安全配置区域添加以下内容，将订阅/发布相关的命令完全禁用
rename-command SUBSCRIBE ""
rename-command PUBLISH ""
rename-command PSUBSCRIBE ""
+ 禁用手段2：通过 ACL 细粒度权限限制 —— 在现代 Redis 版本中，更常用的做法是不给当前连接的系统账号分配 @pubsub 权限组。
# 显式移除账号的 pubsub 权限类目
ACL SETUSER your_java_user -@pubsub
#########################

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

我们需要配置 Redis 订阅一个叫 websocket-bridge 的通道，一旦有任何一台机器发布消息，所有机器都会被通知并执行转发。

创建 RedisStompBridgeConfig.java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisStompBridgeConfig {

    public static final String REDIS_CHANNEL = "websocket-bridge";

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisStompBridgeConfig(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    /**
     * 配置 Redis 消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer redisContainer(RedisConnectionFactory connectionFactory,
                                                        MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        // 让所有服务器节点都订阅统一的 Redis 频道
        container.addMessageListener(listenerAdapter, new ChannelTopic(REDIS_CHANNEL));
        return container;
    }

    /**
     * 绑定具体的业务处理方法
     */
    @Bean
    public MessageListenerAdapter listenerAdapter() {
        return new MessageListenerAdapter(new Object() {
            // 当 Redis 收到消息时，会自动触发这个 handleMessage 方法
            @SuppressWarnings("unused")
            public void handleMessage(String message) {
                try {
                    // 1. 反序列化成我们自定义的消息中转包
                    RedisMessageWrapper wrapper = objectMapper.readValue(message, RedisMessageWrapper.class);
                    
                    // 2. 根据消息类型判断是【广播】还是【私聊】
                    if ("BROADCAST".equals(wrapper.getType())) {
                        // 统一调用本地的内存 Broker 推送给连在这台机器上的所有对应订阅者
                        simpMessagingTemplate.convertAndSend(wrapper.getDestination(), wrapper.getPayload());
                    } else if ("PRIVATE".equals(wrapper.getType())) {
                        // 统一调用本地的内存 Broker 推送给连在这台机器上的指定用户
                        simpMessagingTemplate.convertAndSendToUser(wrapper.getTargetUser(), wrapper.getDestination(), wrapper.getPayload());
                    }
                } catch (Exception e) {
                    System.err.println("解析 Redis 中转消息失败: " + e.getMessage());
                }
            }
        }, "handleMessage");
    }

    /**
     * 定义一个简单易用的消息中转包装类
     */
    public static class RedisMessageWrapper {
        private String type;        // BROADCAST 或 PRIVATE
        private String destination; // 例如 /my-stomp/topic/public
        private String targetUser;  // 私聊目标用户的 userId
        private Object payload;     // 真实的内容数据

        // 无参和全参构造、Getter/Setter (可直接用 Lombok)
        public RedisMessageWrapper() {}
        public RedisMessageWrapper(String type, String destination, String targetUser, Object payload) {
            this.type = type; this.destination = destination; this.targetUser = targetUser; this.payload = payload;
        }
        public String getType() { return type; }
        public String getDestination() { return destination; }
        public String getTargetUser() { return targetUser; }
        public Object getPayload() { return payload; }
    }
}


3. 业务代码发送消息（Controller 改动）
在具体的 Java 代码中，你不再直接使用 messagingTemplate.convertAndSend，
而是通过 redisTemplate 向 Redis 广播，
让 Redis 负责把消息复制到集群的每一台机器上。
修改你的 StompChatController.java：
package com.example.controller;

import com.example.config.RedisStompBridgeConfig;
import com.example.config.RedisStompBridgeConfig.RedisMessageWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class StompChatController {

    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public StompChatController(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 分布式群聊广播
     */
    @MessageMapping("/group-chat")
    public void handleGroupChat(Principal principal, @Payload Map<String, String> message) throws Exception {
        String senderId = principal.getName();
        
        Map<String, String> payload = Map.of(
                "sender", senderId,
                "content", message.get("content"),
                "type", "GROUP"
        );

        // 🌟 核心改变：构建中转包，发布到 Redis 中，通知分布式集群里的所有机器
        RedisMessageWrapper wrapper = new RedisMessageWrapper(
                "BROADCAST", 
                "/my-stomp/topic/public", 
                null, 
                payload
        );

        redisTemplate.convertAndSend(RedisStompBridgeConfig.REDIS_CHANNEL, objectMapper.writeValueAsString(wrapper));
    }

    /**
     * 分布式点对点私聊
     */
    @MessageMapping("/private-chat")
    public void handlePrivateChat(Principal principal, @Payload Map<String, String> message) throws Exception {
        String currentUserId = principal.getName();
        String targetUserId = message.get("targetUserId");
        
        Map<String, String> payload = Map.of(
                "sender", currentUserId,
                "content", message.get("content"),
                "type", "PRIVATE"
        );

        // 🌟 核心改变：不管目标用户在不在这台机器上，直接丢给 Redis 广播。
        // 集群中拥有该 targetUserId 的那台服务器收到后，会自动将其推给对应的浏览器客户端。
        RedisMessageWrapper wrapper = new RedisMessageWrapper(
                "PRIVATE", 
                "/my-stomp/queue/private", // 👈 注意：配合 convertAndSendToUser 时这里不要带虚拟前缀 /user
                targetUserId, 
                payload
        );

        redisTemplate.convertAndSend(RedisStompBridgeConfig.REDIS_CHANNEL, objectMapper.writeValueAsString(wrapper));
    }
}
```


