package com.example.demo01springbootserversimple;

import com.example.demo01springbootserversimple.runtime.WebSocketSessionMgr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class Demo01SpringbootServerSimpleApplication {

    public static void main(String[] args) {
        SpringApplication.run(Demo01SpringbootServerSimpleApplication.class, args);
    }

    @Scheduled(cron = "*/10 * * * * ?")
    public void snapshot() {
        log.info("----------");
        WebSocketSessionMgr.getInstance().travelClients((id, dto) -> {
            log.info("+ {} - {}", id, dto);
        });
    }
}
