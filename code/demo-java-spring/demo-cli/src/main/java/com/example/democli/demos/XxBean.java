package com.example.democli.demos;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@ConditionalOnWebApplication
@Component
public class XxBean {
    @PostConstruct
    public void xx() {
        log.info("XxBean init!");
    }
}
