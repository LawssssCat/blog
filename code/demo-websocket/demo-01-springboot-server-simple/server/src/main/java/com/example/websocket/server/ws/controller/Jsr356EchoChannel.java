package com.example.websocket.server.ws.controller;

import com.example.websocket.server.ws.runtime.WebSocketSessionMgr;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 访问地址:
 * ws://localhost:8080/tomcat/channel/echo
 *
 * 这个类是一个纯粹的 Jakarta WebSocket 端点，Spring 完全不知道它的存在。
 */
@ServerEndpoint("/jsr356/channel/1/echo")
@Component
@Slf4j
public class Jsr356EchoChannel {
    @OnOpen
    public void onOpen(Session session) throws IOException {
        log.info("[tomcat-websocket] 收到新连接, Session ID: {}", session.getId());
        session.getBasicRemote().sendText("[server] 连接成功！");
        WebSocketSessionMgr.getInstance().add(session.getId(), session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        log.info("[tomcat-websocket] 收到来自 {} 的消息: {}", session.getId(), message);
        if ("bye".equalsIgnoreCase(message)) {
            // 由服务器主动关闭连接。状态码为 NORMAL_CLOSURE（正常关闭）。
            session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Bye"));
            return;
        }
        String text = "[server] " + message;
        WebSocketSessionMgr.getInstance().sendMsg(session.getId(), text);
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        log.info("[tomcat-websocket] 连接关闭, Session ID: {} , 原因: {}", session.getId(), closeReason.getReasonPhrase());
        WebSocketSessionMgr.getInstance().remove(session.getId(), closeReason);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("[tomcat-websocket] 发生错误, Session ID: {}", session.getId(), error);
//        WebSocketSessionMgr.getInstance().remove(session.getId(), new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }
}
