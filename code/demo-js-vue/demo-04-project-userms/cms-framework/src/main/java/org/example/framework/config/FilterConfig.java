package org.example.framework.config;

import org.example.common.filter.SomeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<SomeFilter> someFilterRegistration() {
        FilterRegistrationBean<SomeFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SomeFilter());
        registration.addUrlPatterns("/*");
        registration.setName("comeFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }
}
