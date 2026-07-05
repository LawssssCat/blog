package com.example.websocket.server.ws.controller;

import com.example.websocket.model.dto.ChatRequest;
import com.example.websocket.model.dto.ChatResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import jakarta.annotation.Resource;
import java.security.Principal;

@Controller
@Slf4j
public class StompChatController {
    /**
     * 业务 hello 的处理。
     *
     * <pre>
     * 主动： 客户端 -- {昵称，消息} --> 服务端 /xxx/app/hello
     * 转发： 服务端 -- {昵称，消息} --> 所有订阅该路径的客户端 /xxx/topic/greetings
     * </pre>
     */
    @MessageMapping("/hello") // 匹配客户端发送的 /xxx/app/hello
    @SendTo("/xxx/topic/greetings") // 将方法的返回值自动广播到该通道
    public ChatResponse handleHelloMessage(@Payload ChatRequest request) {
        log.info("收到客户端 STOMP 消息: 用户={}, 内容={}", request.getSender(), request.getContent());

        // 执行业务逻辑，生成响应对象
        String replyContent = "全世界听好了！客户端 " + request.getSender() + " 上线了！";
        return new ChatResponse("System", replyContent);
    }


    // ==========================================
    // 进阶：如果不想用 @SendTo 自动广播，可以使用这个工具类手动、灵活地发送
    // ==========================================
    @Resource
    private SimpMessagingTemplate messagingTemplate;

    /**
     * 业务 private-chat 的处理
     *
     * <pre>
     * 主动： 客户端 -- {昵称，消息} --> 服务端 /xxx/app/private-chat
     * 转发： 服务端 -- 分析出私聊对象 --> {昵称，消息} --> /xxx/user/xxx
     * </pre>
     */
    @MessageMapping("/private-chat")
    public void handlePrivateMessage(@Payload ChatRequest request, Principal principal) {
        String currentClientId = principal.getName();
        log.info("【收到私聊】当前发送消息的客户端 ID 是: {}", currentClientId);
        log.info("收到消息，准备执行复杂逻辑... {} | {}", principal, currentClientId);
//        String receiver = "target_user_id"; // 从 request 或业务中获取接收方ID

        // 逻辑 A：可以先存进数据库...

        // 逻辑 B：根据业务条件，决定发给谁。比如发送到指定的频道：
        ChatResponse response = new ChatResponse(request.getSender(), request.getContent());
        messagingTemplate.convertAndSendToUser(currentClientId, "/xxx/queue/my-private", response); // 私聊发回给自己

        // mock 回复一个大消息
//        ChatResponse response2 = new ChatResponse(request.getSender(), new String(new byte[1024]));
        ChatResponse response2 = new ChatResponse(request.getSender(), new String(new byte[1024 * 1024]));
        messagingTemplate.convertAndSendToUser(currentClientId, "/xxx/queue/my-private", response2);
        try {
            log.info("ok {}", new ObjectMapper().writeValueAsString(response2).length());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
