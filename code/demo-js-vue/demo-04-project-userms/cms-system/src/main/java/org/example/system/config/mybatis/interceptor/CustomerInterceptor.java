package org.example.system.config.mybatis.interceptor;

import java.util.Arrays;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class CustomerInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        log.info("-------- {}, {}, {}", invocation.getTarget(), invocation.getMethod(), Arrays.toString(invocation.getArgs()));
        return invocation.proceed();
    }
}
