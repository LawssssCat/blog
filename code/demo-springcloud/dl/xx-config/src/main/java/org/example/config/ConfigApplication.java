package org.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@EnableConfigServer
@SpringBootApplication
@Slf4j
public class ConfigApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigApplication.class, args);

        // 方法 1：通过 System 属性获取（最推荐）
        String userDir = System.getProperty("user.dir");
        log.info("====== 当前工作目录 (System Property) ====== \nuser.dir = {}", userDir);
    }
}
