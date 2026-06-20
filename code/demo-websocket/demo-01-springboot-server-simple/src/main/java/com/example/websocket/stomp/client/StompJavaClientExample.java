package com.example.websocket.stomp.client;

import com.example.websocket.stomp.model.dto.ChatRequest;
import com.example.websocket.stomp.model.dto.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.lang.reflect.Type;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

@Slf4j
public class StompJavaClientExample {
    private static final CountDownLatch disconnectLatch = new CountDownLatch(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 1. 创建底层的 WebSocket 物理连接客户端
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        // 2. 包装为上层的 STOMP 协议客户端
        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        // 3. 核心选型配置：必须配置 Jackson 转换器，这样发送的对象才能自动转为 JSON 字符串
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // ========== 配置心跳
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(1);
        taskScheduler.setThreadNamePrefix("stomp-heartbeat-");
        taskScheduler.initialize(); // 必须显式初始化
        stompClient.setTaskScheduler(taskScheduler); // 注入调度器
        stompClient.setDefaultHeartbeat(new long[]{10000, 10000}); // 每10秒双向互发心跳


        // 服务端的物理握手连接地址 (对应配置中的 registerStompEndpoints)
//        String url = "ws://localhost:23380/ws-endpoint";
        String url = "ws://localhost:23380/ws-endpoint?userId=target_user_id"; // 能收到私发的消息
        log.info("正在连接服务端...");
        // 4. 发起异步连接，并传入自定义的生命周期处理器
        ListenableFuture<StompSession> connect = stompClient.connect(url, new MyStompSessionHandler());
        // 核心心跳配置：[客户端发送心跳间隔, 客户端接收心跳间隔] 单位毫秒
        // 10000, 10000 表示每 10 秒双方互发一次 Ping/Pong 报文
//        stompClient.setDefaultHeartbeat(new long[]{10000, 10000});
//        stompClient.setTaskScheduler(this.taskScheduler);

        log.info("-------- wait end -------------");
        connect.completable().get();
        // 保持主线程阻塞运行，防止 JVM 退出
//        Thread.currentThread().join();
//        Thread.sleep(10 * 1000);
        disconnectLatch.await();
        log.info("主线程检测到连接已彻底断开，正在安全退出 JVM...");
        taskScheduler.shutdown(); // 优雅关闭心跳线程池
    }

    /**
     * 自定义 STOMP 会话处理器，用于处理连接建立、断开、异常以及消息监听
     */
    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {

        @Override
        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
            log.info("STOMP 协议连接成功! 分配的会话 ID: " + session.getSessionId());

            // ==================== 1. 订阅公共广播通道 ====================
            // 【订阅消息广播通道】监听服务器往 /topic/greetings 推送的所有消息
            session.subscribe("/topic/greetings", new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    // 告诉 Spring，把收到的 JSON 报文自动反序列化为什么 Java 类
                    return ChatResponse.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    // 当收到服务器广播时触发此回调
                    ChatResponse response = (ChatResponse) payload;
                    log.info("[收到订阅消息] 来自: {}, 内容: {}", response.getFrom(), response.getContent());
                }
            });

            // ==================== 2. 新增：订阅个人私聊通道 ====================
            // 注意：必须以 /user/ 开头。Spring 会自动处理并路由到当前登录用户或当前 Session 对应的队列
            session.subscribe("/user/queue/private", new StompFrameHandler() {
                @Override
                public Type getPayloadType(StompHeaders headers) {
                    return ChatResponse.class;
                }

                @Override
                public void handleFrame(StompHeaders headers, Object payload) {
                    ChatResponse response = (ChatResponse) payload;
                    log.info("[🔔收到私聊消息] 来自: {}, 内容: {}", response.getFrom(), response.getContent());
                }
            });

            // ==================== 3. 向公共入口发送消息（自定义的业务处理，会将这个请求广播） ====================
            // 【向业务处理入口发送消息】构造后端的 Controller 需要的 ChatRequest 对象
            ChatRequest appRequest = new ChatRequest("Java客户端_01", "这是从后端微服务发来的问候");
            log.info("正在向 /app/hello 发送请求数据...");
            // 发送数据。由于配置了 JacksonConverter，这个对象会自动被打包成 STOMP SEND 帧的 JSON body
            session.send("/app/hello", appRequest);

            // ==================== 4. 新增：向特定用户发送私聊消息 ====================
            // 假设服务端对应的处理入口是 @MessageMapping("/private-chat")
            // 我们可以在 Payload 里面指定接收人，或者在 STOMP Header 里面携带接收人标识
            ChatRequest privateRequest = new ChatRequest("Java客户端_01", "嘿，这是一条私聊消息");
            // 示例 A：如果你的服务端 Controller 靠路由参数接收（如 /app/chat/{targetUser}）
            // session.send("/app/chat/user_999", privateRequest);
            // 示例 B：标准做法，将私聊请求发送到统一的私聊业务入口，由服务端根据请求体内的 targetUser 转发
            log.info("正在向 /app/private-chat 发送私聊请求...");
            session.send("/app/private-chat", privateRequest);
        }

        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
            System.err.println("STOMP 协议处理发生异常: " + exception.getMessage());
            exception.printStackTrace();
        }

        @Override
        public void handleTransportError(StompSession session, Throwable exception) {
            System.err.println("网络传输异常（如服务端停机或网络断开）: " + exception.getMessage());
            disconnectLatch.countDown();
        }
    }
}
