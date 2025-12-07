package com.example.toolserverhttpcounter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ToolServerHttpCounterApplication {
    public static void main(String[] args) {
        SpringApplication.run(ToolServerHttpCounterApplication.class, args);
    }
}
