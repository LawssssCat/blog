package com.example.websocket.server.ws.controller;

import com.example.websocket.server.ws.runtime.WebSocketSessionMgr;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.Instant;

/**
 * WebSocket端点，通过value注解指定websocket的路径
 */
@ServerEndpoint(value = "/jsr356/channel/2/echo/{topic}")
@Slf4j
public class Jsr356Echo2Channel {
    // 收到消息
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("topic") Long topic) throws IOException {
        log.info("[websocket] 收到消息：id={}，topic={}，message={} [{}]", session.getId(), topic, message, this);
        if ("bye".equalsIgnoreCase(message)) {
            // 由服务器主动关闭连接。状态码为 NORMAL_CLOSURE（正常关闭）。
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye"));
            return;
        }
        String text = "[server] [" + Instant.now().toEpochMilli() + "] Hello " + message;
        WebSocketSessionMgr.getInstance().sendMsg(session.getId(), text);
    }

    // 连接打开
    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig, @PathParam("topic") Long topic) {
        // 保存 session 到对象
        log.info("[websocket] 新的连接：id={}，topic={}, config={}", session.getId(), topic, endpointConfig);
        WebSocketSessionMgr.getInstance().add(session.getId(), session);
    }

    // 连接关闭
    @OnClose
    public void onClose(Session session, CloseReason closeReason, @PathParam("topic") Long topic) {
        log.info("[websocket] close 连接断开：id={}，topic={}，reason={} [{}]", session.getId(), topic, closeReason, this);
        WebSocketSessionMgr.getInstance().remove(session.getId(), closeReason);
    }

    // 连接异常
    @OnError
    public void onError(Session session, Throwable throwable, @PathParam("topic") Long topic) throws IOException {
        log.info("[websocket] error 连接异常：id={}，topic={}，throwable={} [{}]", session.getId(), topic, throwable.getMessage(), this);
        // 异常 ！= 关闭，否则连接会非常不稳定
        // 关闭连接。状态码为 UNEXPECTED_CONDITION（意料之外的异常）
//        WebSocketSessionMgr.getInstance().remove(session.getId(), new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }
}
