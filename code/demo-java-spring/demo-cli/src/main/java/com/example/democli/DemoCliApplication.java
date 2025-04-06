package com.example.democli;

import com.example.democli.demos.XxBean;
import com.example.democli.demos.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@SpringBootApplication
public class DemoCliApplication implements ApplicationRunner {

    @Resource
    private UserMapper userMapper;

    @Autowired(required = false)
    private XxBean xxBean;

    public static void main(String[] args) {
        System.out.println("Arrays.toString(args) = " + Arrays.toString(args));
//        SpringApplication.run(DemoCliApplication.class, args);
        SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(DemoCliApplication.class);
        if (Arrays.asList(args).contains("--plain")) {
            springApplicationBuilder.web(WebApplicationType.NONE);
        }
        springApplicationBuilder.run(args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1. DAO 加载
        log.info("usermapper: {} - {}", userMapper, userMapper.selectAll());
        // 1. 条件bean初始化
        System.out.println("xxBean: " + xxBean);
        // 1. 条件任务执行
        if (args.getOptionNames().contains("plain")) {
            System.out.println("hello~");
        }
    }
}
