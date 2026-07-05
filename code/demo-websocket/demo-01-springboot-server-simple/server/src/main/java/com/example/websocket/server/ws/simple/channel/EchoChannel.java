package com.example.websocket.server.ws.simple.channel;

import com.example.websocket.server.ws.simple.runtime.WebSocketSessionMgr;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;

/**
 * WebSocket端点，通过value注解指定websocket的路径
 */
@ServerEndpoint(value = "/channel/echo/{topic}")
@Slf4j
public class EchoChannel {
    /**
     * 表示当前连接对象，我们可以通过此对象来执行发送消息、断开连接等操作。
     * WebSocket 的连接 URL，类似于 Http 的 URL，也可以传递查询参数、path 参数。
     * 通常用于传递认证、鉴权用的 Token 或其他信息。
     *
     * <pre><code>
     * Map<String, List<String>> query = session.getRequestParameterMap();
     * </code></pre>
     */
    private Session session;

    /**
     * 自定义参数
     */
    private Long topic;

    // 收到消息
    @OnMessage
    public void onMessage(String message) throws IOException {
        log.info("[websocket] message 收到消息：id={}，topic={}，message={} [{}]", this.session.getId(), this.topic, message, this);

        if ("bye".equalsIgnoreCase(message)) {
            // 由服务器主动关闭连接。状态码为 NORMAL_CLOSURE（正常关闭）。
            this.session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye"));
            return;
        }
        String text = "[" + Instant.now().toEpochMilli() + "] Hello " + message;
        WebSocketSessionMgr.getInstance().sendMsg(String.valueOf(this.topic), text);
    }

    // 连接打开
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig, @PathParam("topic") Long topic) {
        // 保存 session 到对象
        this.session = session;
        this.topic = topic;
        log.info("[websocket] open 新的连接：id={}，topic={}, config={} [{}]", this.session.getId(), this.topic, endpointConfig, this);
        WebSocketSessionMgr.getInstance().add(String.valueOf(topic), session);
    }

    // 连接关闭
    @OnClose
    public void onClose(CloseReason closeReason) {
        log.info("[websocket] close 连接断开：id={}，topic={}，reason={} [{}]", this.session.getId(), this.topic, closeReason, this);
        WebSocketSessionMgr.getInstance().remove(String.valueOf(this.topic), closeReason);
    }

    // 连接异常
    @OnError
    public void onError(Throwable throwable) throws IOException {
        log.info("[websocket] error 连接异常：id={}，topic={}，throwable={} [{}]", this.session.getId(), this.topic, throwable.getMessage(), this);
        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
        WebSocketSessionMgr.getInstance().remove(String.valueOf(this.topic), new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }
}
