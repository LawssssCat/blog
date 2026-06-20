package org.example.websocket.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.PingMessage;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.nio.ByteBuffer;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class SimpleClientExample {
    // 控制主线程阻塞，直到连接彻底断开
    private static final CountDownLatch disconnectLatch = new CountDownLatch(1);
    // 用于定时发送心跳的线程池
    private static final ScheduledExecutorService heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();

    private static final String topicId = "88888"; // 服务端会把它解析为 Long topic
    private static final String url = "ws://localhost:32122/channel/echo/" + topicId;

    // 记录连续重连失败的次数
    private static final AtomicInteger reconnectAttempts = new AtomicInteger(0);
    // 最大重连等待间隔（秒），避免无限拉长等待时间
    private static final int MAX_RECONNECT_BACKOFF_SEC = 60;

    private static ScheduledFuture<?> heartbeatTask = null;
    private static WebSocketSession currentSession = null;

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 1. 创建原生 WebSocket 客户端
        StandardWebSocketClient webSocketClient = new StandardWebSocketClient();
        // 包装为STOMP客户端
//        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
        // 可选：配置消息转换器（例如支持JSON）
        // stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        // 2. 定义连接 URL，动态传入你的 topic（对应服务端的 @PathParam("topic")）
        String topicId = "88888"; // 服务端会把它解析为 Long topic
        String url = "ws://localhost:32122/channel/echo/" + topicId;
//        String url = "ws://localhost:32122/channel/echo/1";
        log.info("正在连接原生 WebSocket 服务端: {}", url);
//        stompClient.connectAsync(url, new MyStompSessionHandler());
        // 3. 发起连接，并传入事件处理器
        WebSocketSession session = webSocketClient.execute(new MyWebSocketHandler(), url).get();

        log.info("-------- 客户端连接成功，进入运行等待阶段 -------------");
        // 4. 启动手动心跳：每 10 秒向服务端发送一次标准 Ping 帧
        // ❗有服务端心跳了，不需要客户端心跳
//        heartbeatScheduler.scheduleAtFixedRate(() -> {
//            try {
//                if (session.isOpen()) {
//                    log.info("💓 [客户端] 正在向服务端发送标准 Ping 心跳...");
//                    // 发送底层的标准 Ping 消息（Tomcat 等容器收到后会自动回复 Pong）
//                    session.sendMessage(new PingMessage(ByteBuffer.wrap(new byte[0])));
//                }
//            } catch (Exception e) {
//                log.error("发送心跳失败: {}", e.getMessage());
//            }
//        }, 10, 10, TimeUnit.SECONDS);

        // 5. 核心：主线程在此永久阻塞，直到网络断开触发 countDown
        disconnectLatch.await();
        log.info("主线程检测到连接已彻底断开，正在清理资源并退出 JVM...");
        heartbeatScheduler.shutdown();
    }

//    private static class MyStompSessionHandler extends StompSessionHandlerAdapter {
//        @Override
//        public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
//            log.info("--------- 连接成功！ Session ID = {}", session.getSessionId());
//        }
//    }
    /**
     * 自定义原生 WebSocket 消息处理器
     */
    private static class MyWebSocketHandler extends TextWebSocketHandler {

        // 连接建立成功（对应服务端的 @OnOpen）
        @Override
        public void afterConnectionEstablished(WebSocketSession session) throws Exception {
            log.info("🚀 物理连接已建立！分配的客户端本地 SessionID: {}", session.getId());

            // 连上之后，立即向服务端发送一条业务消息
            String initMessage = "这是一条来自 Java 客户端的问候消息";
            session.sendMessage(new TextMessage(initMessage));
        }

        // 收到服务端发来的文本消息（对应服务端的 @OnMessage 中 WebSocketSessionMgr 发出的消息）
        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
            // 🎯 这里就是你“接收私聊/群聊”的地方。
            // 只要服务端的 WebSocketSessionMgr.getInstance().sendMsg() 找到了你这个 topic 的 session，
            // 就会把消息推送到这里。
            log.info("📩 [收到服务端推送] 内容: {}", message.getPayload());
        }

        // 处理底层收到的标准 Pong 消息
        @Override
        protected void handlePongMessage(WebSocketSession session, org.springframework.web.socket.PongMessage message) throws Exception {
            log.info("💗 [客户端收到服务端的 Pong 回应] 连接健康！");
        }

        // 连接断开或传输异常（对应服务端的 @OnClose 或 @OnError）
        @Override
        public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
            log.error("⚠️ [网络传输异常] 原因: {}", exception.getMessage());
        }

        // 连接彻底关闭
        @Override
        public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
            log.warn("❌ [连接关闭] 服务端连接已断开！状态码: {}, 原因: {}", status.getCode(), status.getReason());

            // 🎯 核心：断开时释放闭锁，唤醒主线程优雅退出或进行重连
            disconnectLatch.countDown();
        }
    }
}
