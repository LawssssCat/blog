package com.example.websocket.server.ws.config;

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

/**
 * 用于开启 Spring 高级消息代理（STOMP/SockJS） 架构。
 * 它是一套功能完备的“全家桶”框架，自带应用层协议、房间路由和发布订阅（Pub/Sub）功能。
 *
 * /my-stomp/endpoint —— 服务端建立连接的前缀
 * /my-stomp/app —— 服务端接收消息的前缀
 * /my-stomp/topic —— 服务端/客户端广播的前缀
 * /my-stomp/queue —— 同上
 * /my-stomp/user —— 服务端私聊的前缀
 */
@Configuration
@EnableWebSocketMessageBroker
@Slf4j
public class StompWebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 1. 连接建立地址
        registry.addEndpoint(
                "/my-stomp/endpoint"
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
        // 2. 服务端，Controller监听的业务处理前缀：
        registry.setApplicationDestinationPrefixes("/xxx/app");

        // 3. 服务端，消息广播/私发的前缀。
        // 客户端，监听广播消息：
        // /xxx/topic
        // /xxx/queue
        registry.enableSimpleBroker("/xxx/topic", "/xxx/queue");
        // 4. 服务端，私发前缀。
        // 客户端监听私发消息
        // /xxx/user/xxx/topic
        // /xxx/user/xxx/queue
        registry.setUserDestinationPrefix("/xxx/user");
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
