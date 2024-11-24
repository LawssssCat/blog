package org.example.framework.config;

import org.example.common.filter.SomeFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Filter配置
 */
@Configuration
public class FilterConfig {
    // 没用
    @Bean
    public FilterRegistrationBean<SomeFilter> someFilterRegistration() {
        FilterRegistrationBean<SomeFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new SomeFilter());
        registration.addUrlPatterns("/*");
        registration.setName("comeFilter");
        registration.setOrder(FilterRegistrationBean.HIGHEST_PRECEDENCE);
        return registration;
    }

    /**
     * 跨域设置
     *
     * <br>
     * FIX: Access to XMLHttpRequest at 'http://localhost:8099/api/login' from origin 'http://localhost:5173' has been
     * blocked by CORS policy: Response to preflight request doesn't pass access control check: No
     * 'Access-Control-Allow-Origin' header is present on the requested resource.
     */
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 支持用户凭据
        // 设置访问源地址
        config.addAllowedOriginPattern("*");
        // 设置访问源请求头
        config.addAllowedHeader("*");
        // 设置访问源请求方法
        config.addAllowedMethod("*");
        // 有效期
        config.setMaxAge(1800L);
        // 添加映射路径：拦截一切请求
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        // 返回新的CorsFilter
        return new CorsFilter(source);
    }
}
