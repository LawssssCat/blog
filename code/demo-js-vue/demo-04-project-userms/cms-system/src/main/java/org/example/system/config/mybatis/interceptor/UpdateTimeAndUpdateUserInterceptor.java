package org.example.system.config.mybatis.interceptor;

import java.util.Date;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.example.common.core.domain.BaseEntity;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义 Mybatis 插件，自动设置 createTime 和 updatTime 的值。 <br>
 * 拦截 update 操作（添加和修改）
 */
@Slf4j
@Intercepts({
    @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class UpdateTimeAndUpdateUserInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        if (parameter instanceof BaseEntity) {
            String account = "x"; // TODO 获取登录用户
            Date time = new Date();

            BaseEntity p = (BaseEntity)parameter;
            p.setUpdateTime(time);
            p.setUpdateBy(account);

            SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                p.setCreateTime(time);
                p.setCreateBy(account);
            }
        }
        return invocation.proceed();
    }
}
