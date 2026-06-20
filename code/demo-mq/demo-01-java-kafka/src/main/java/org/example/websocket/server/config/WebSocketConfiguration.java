package org.example.websocket.server.config;

import lombok.extern.slf4j.Slf4j;
import org.example.websocket.server.channel.EchoChannel;
import org.example.websocket.server.runtime.WebSocketSessionMgr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
@EnableScheduling
@Slf4j
public class WebSocketConfiguration {
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        ServerEndpointExporter exporter = new ServerEndpointExporter();
        // 手动注册 WebSocket 端点
        // 也可以在 WebSocket 端点上添加 @Component 注解，使用 Spring 自动扫描，这样的话不需要手动调用 setAnnotatedEndpointClasses 方法进行注册。
        exporter.setAnnotatedEndpointClasses(EchoChannel.class);
        return exporter;
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void snapshot() {
        log.info("----------");
        WebSocketSessionMgr.getInstance().travelClients((id, dto) -> {
            log.info("+ {} - {}", id, dto);
        });
    }
}
