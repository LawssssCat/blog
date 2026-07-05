package com.example.websocket.server.ws.config;

import com.example.websocket.server.ws.controller.Jsr356Echo2Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * 用于开启 Java 官方原生的 JSR 356（Jakarta WebSocket） 规范。
 * 它极为底层、轻量，适合纯粹的“管道式”点对点长连接。
 */
@Configuration
@EnableScheduling
@Slf4j
public class SimpleWebSocketConfiguration {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        ServerEndpointExporter exporter = new ServerEndpointExporter();
        // 手动注册 WebSocket 端点
        // 也可以在 WebSocket 端点上添加 @Component 注解，使用 Spring 自动扫描，这样的话不需要手动调用 setAnnotatedEndpointClasses 方法进行注册。
        exporter.setAnnotatedEndpointClasses(Jsr356Echo2Channel.class);
        return exporter;
    }
}
