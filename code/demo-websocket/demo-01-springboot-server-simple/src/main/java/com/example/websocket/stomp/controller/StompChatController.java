package com.example.websocket.stomp.controller;

import com.example.websocket.stomp.model.dto.ChatRequest;
import com.example.websocket.stomp.model.dto.ChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.security.Principal;

@Controller
@Slf4j
public class StompChatController {
    /**
     * 1. 自动路由 + 自动广播模式
     * 客户端发送目的地：/app/hello (因为配置了 /app 前缀)
     * 执行完后，返回值会自动发送到：/topic/greetings
     */
    @MessageMapping("/hello") // 匹配客户端发送的 /app/hello
    @SendTo("/topic/greetings") // 将方法的返回值自动广播到该通道
    public ChatResponse handleHelloMessage(ChatRequest request) {
        log.info("收到客户端 STOMP 消息: 用户={}, 内容={}", request.getSender(), request.getContent());

        // 执行业务逻辑，生成响应对象
        String replyContent = "服务器收到消息了！Hello, " + request.getSender();
        return new ChatResponse("System", replyContent);
    }


    // ==========================================
    // 进阶：如果不想用 @SendTo 自动广播，可以使用这个工具类手动、灵活地发送
    // ==========================================
    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 2. 灵活手动发送模式
     * 客户端发送目的地：/app/private-chat
     */
    @MessageMapping("/private-chat")
    public void handlePrivateMessage(ChatRequest request, Principal principal) {
        log.info("收到消息，准备执行复杂逻辑...");
        String receiver = "target_user_id"; // 从 request 或业务中获取接收方ID

        // 逻辑 A：可以先存进数据库...

        // 逻辑 B：根据业务条件，决定发给谁。比如发送到指定的频道：
        ChatResponse response = new ChatResponse(request.getSender(), request.getContent());
        messagingTemplate.convertAndSend("/topic/room-1", response);

        // 或者是实现单对单私聊（发给特定用户，配合 /queue 前缀使用）：
        // messagingTemplate.convertAndSendToUser("某个用户ID", "/queue/chat", response);
        messagingTemplate.convertAndSendToUser(receiver, "/queue/private", response);
    }
}
