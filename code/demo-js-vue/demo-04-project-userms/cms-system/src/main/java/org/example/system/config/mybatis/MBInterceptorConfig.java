package org.example.system.config.mybatis;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.system.config.mybatis.interceptor.CustomerInterceptor;
import org.example.system.config.mybatis.interceptor.UpdateTimeAndUpdateUserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MBInterceptorConfig {
    @Bean
    public CustomerInterceptor customerInterceptor() {
        return new CustomerInterceptor();
    }

    @Bean
    public UpdateTimeAndUpdateUserInterceptor updateTimeAndUpdateUserInterceptor() {
        return new UpdateTimeAndUpdateUserInterceptor();
    }

    // 坑：在依赖 PageHelper 的情况下，下面注册方式不会生效
    // @Bean
    // public ConfigurationCustomizer configurationCustomizer() {
    // return new ConfigurationCustomizer() {
    // @Override
    // public void customize(org.apache.ibatis.session.Configuration configuration) {
    // configuration.addInterceptor(customerInterceptor());
    // }
    // };
    // }
    @Resource
    List<SqlSessionFactory> sqlSessionFactories;

    @PostConstruct
    public void postConstruct() {
        log.debug("begin to register customer mybatis interceptor");
        for (SqlSessionFactory sqlSessionFactory : sqlSessionFactories) {
            sqlSessionFactory.getConfiguration().addInterceptor(customerInterceptor());
            sqlSessionFactory.getConfiguration().addInterceptor(updateTimeAndUpdateUserInterceptor());
        }
        log.debug("finish register customer mybatis interceptor");
    }
}
