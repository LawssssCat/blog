package org.example.cache.config;

import org.example.cache.context.CacheContext;
import org.example.cache.context.H2CacheContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class CacheConfig {
    @Value("${cache.type:h2}")
    private String type;

    @Bean
    public CacheContext cacheContext() throws Exception {
        log.info("cache type: {}", type);
        return new H2CacheContext();
    }
}
