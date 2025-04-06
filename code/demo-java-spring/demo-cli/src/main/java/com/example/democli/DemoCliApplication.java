package com.example.democli;

import com.example.democli.demos.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

@Slf4j
@SpringBootApplication
public class DemoCliApplication implements ApplicationRunner {

    @Resource
    private UserMapper userMapper;

    public static void main(String[] args) {
        SpringApplication.run(DemoCliApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("usermapper: {} - {}", userMapper, userMapper.selectAll());
    }
}
