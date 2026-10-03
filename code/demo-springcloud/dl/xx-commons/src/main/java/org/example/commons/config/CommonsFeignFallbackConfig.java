package org.example.commons.config;

import org.example.commons.service.GoodsRemoteClientFallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonsFeignFallbackConfig {
    @Bean
    public GoodsRemoteClientFallback goodsRemoteClientFallback() {
        return new GoodsRemoteClientFallback();
    }
}
