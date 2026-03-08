package com.example.flink;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class FlinkJavaApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(FlinkJavaApplication.class);

    public static void main(String[] args) {
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(FlinkJavaApplication.class);
        springApplicationBuilder.listeners(new ApplicationListener<ApplicationEvent>() {
            @Override
            public void onApplicationEvent(ApplicationEvent event) {
                LOGGER.info("<==== {}", event);
            }
        });
        springApplicationBuilder.run(args);
        LOGGER.info("main ok ");
    }

}
