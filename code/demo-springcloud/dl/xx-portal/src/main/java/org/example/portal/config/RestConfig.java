package org.example.portal.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RoundRobinRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

//@Configuration // 使用Feign实现接口调用
public class RestConfig {
    @LoadBalanced // 使用Ribbon实现负载均衡调用
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    // 设置Ribbon负载均衡算法
    @Bean
    public IRule iRule() {
        return new RoundRobinRule();
    }
}
