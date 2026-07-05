package com.example.websocket.server.ws.stomp.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.handler.WebSocketSessionDecorator;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 1. 连接建立地址
        registry.addEndpoint(
                "/ws-endpoint"
        ).setHandshakeHandler(new DefaultHandshakeHandler() {
            @Override
            protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                UriComponents uriComponents = UriComponentsBuilder.fromUri(request.getURI())
                        .build();
                String userId = uriComponents.getQueryParams().getFirst("userId");
                return () -> userId == null ? "anonymous" : userId;
            }
        }).setAllowedOrigins("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 2. 业务处理前缀：凡是 /app/... 的消息都进入 Controller 处理
        registry.setApplicationDestinationPrefixes("/app");

        // 3. 消息广播/订阅通道的前缀：凡是 /topic/... 的消息都进入内存代理直接广播
        // 处理逻辑：
        // 当客户端/服务端执行 session.send("/topic/greetings", msg) 时，
        // 消息不经过任何 Java 代码（Controller），直接由 Broker 复制并分发给所有订阅了该主题的客户端。
        // 适用场景：
        // 不需要鉴权、不需要存数据库、纯粹的“前端对前端”实时同步（例如：匿名聊天室、实时画板）。
        registry.enableSimpleBroker("/topic", "/queue");
    }


//    🎯 核心原因：
//    Spring 默认的内存消息代理（SimpleBroker）不经过 Channel 拦截器发送心跳
//    在 Spring STOMP 的架构中：
//    1. 输入心跳 (Inbound)：
//       客户端发给服务端的心跳，在网络层被底层的 WebSocket 容器（如 Tomcat/Jetty）接收后，直接传递给了心跳监测器（HeartbeatHandler），
//       它根本没有转化为标准的 Message 对象投递到 Spring 的 MessageChannel 中。
//       因此 configureClientInboundChannel 无法拦截到。
//    2. 输出心跳 (Outbound)：
//       如果你使用的是内置的 SimpleBroker（内存代理），它的心跳发送任务是由底层的定时器线程通过物理 WebSocketSession.sendMessage() 直接写入 TCP 通道的，
//       同样绕过了 Spring 的 ClientOutboundChannel。
//    也就是说，在这两个通道拦截器里，accessor.getCommand() == null 的判断永远不会被触发。
//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        // 添加输入通道拦截器：拦截客户端发往服务端的所有消息（包括心跳）
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//                // 🎯 核心判断：如果 STOMP 命令为空，说明这是一条纯粹的心跳 PING 报文
//                if (accessor.getCommand() == null) {
//                    log.info("💓 [服务端收到客户端心跳] SessionID: {}", accessor.getSessionId());
//                }
//
//                return message;
//            }
//        });
//    }
//
//    @Override
//    public void configureClientOutboundChannel(ChannelRegistration registration) {
//        // 添加输出通道拦截器：拦截服务端发往客户端的所有消息（包括心跳转发）
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(Message<?> message, MessageChannel channel) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//
//                if (accessor.getCommand() == null) {
//                    log.info("💗 [服务端向客户端发送心跳] SessionID: {}", accessor.getSessionId());
//                }
//
//                return message;
//            }
//        });
//    }

    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registry) {
        registry.addDecoratorFactory(new WebSocketHandlerDecoratorFactory() {
            @Override
            public WebSocketHandler decorate(WebSocketHandler handler) {
                return new WebSocketHandlerDecorator(handler) {

                    // 1. 拦截所有【进入服务端】的 WebSocket 消息
                    @Override
                    public void handleMessage(WebSocketSession session, org.springframework.web.socket.WebSocketMessage<?> message) throws Exception {
                        if (message instanceof TextMessage) {
                            String payload = ((TextMessage) message).getPayload();
                            // STOMP 协议规定，心跳报文就是一个纯粹的换行符 \n 或者是 \r\n
                            if ("\n".equals(payload) || "\r\n".equals(payload)) {
                                log.info("💓 [物理层：服务端收到客户端心跳] SessionID: {}", session.getId());
                            }
                        }
                        log.info("-------- {}", message);
                        super.handleMessage(session, message);
                    }

                    @Override
                    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                        WebSocketSession wrappedSession = new WebSocketSessionDecorator(session) {
                            @Override
                            public void sendMessage(WebSocketMessage<?> message) throws IOException {
                                if (message instanceof TextMessage) {
                                    String payload = ((TextMessage) message).getPayload();
                                    // 拦截服务端发出的心跳 (\n)
                                    if ("\n".equals(payload) || "\r\n".equals(payload)) {
                                        log.info("💗 [物理层：服务端向客户端发送心跳] SessionID: {}", getId());
                                    }
                                }
                                log.info("-------- {}", message);
                                super.sendMessage(message);
                            }
                        };
                        super.afterConnectionEstablished(wrappedSession);
                    }

                };
            }
        });
    }
}
