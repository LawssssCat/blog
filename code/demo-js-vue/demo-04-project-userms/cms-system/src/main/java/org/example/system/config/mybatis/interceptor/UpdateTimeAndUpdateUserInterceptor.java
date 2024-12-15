package org.example.system.config.mybatis.interceptor;

import java.util.Collection;
import java.util.Date;
import java.util.Map;

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

    private ThreadLocal<Date> threadLocalDate = new ThreadLocal<>();

    private ThreadLocal<String> threadLocalAccount = new ThreadLocal<>();

    private Date getThreadLocalDate() {
        Date date = threadLocalDate.get();
        if (date == null) {
            threadLocalDate.set(new Date());
        }
        return threadLocalDate.get();
    }

    private String getThreadLocalAccount() {
        String account = threadLocalAccount.get();
        if (account == null) {
            threadLocalAccount.set("xxx");
        }
        return threadLocalAccount.get();
    }

    private void cleanThreadLocal() {
        threadLocalAccount.remove();
        threadLocalDate.remove();
    }

    private void fillItem(BaseEntity baseEntity, SqlCommandType sqlCommandType) {
        String account = getThreadLocalAccount();
        Date time = getThreadLocalDate();

        baseEntity.setUpdateTime(time);
        baseEntity.setUpdateBy(account);

        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            baseEntity.setCreateTime(time);
            baseEntity.setCreateBy(account);
        }
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];

        if (parameter instanceof BaseEntity) {
            fillItem((BaseEntity)parameter, mappedStatement.getSqlCommandType());
        } else if (parameter instanceof Collection) {
            fillCollection((Collection)parameter, mappedStatement);
        } else if (parameter instanceof Map) {
            fillMap((Map)parameter, mappedStatement);
        }

        cleanThreadLocal();
        return invocation.proceed();
    }

    private void fillMap(Map map, MappedStatement mappedStatement) {
        if (map.get("list") instanceof Collection) {
            fillCollection((Collection)map.get("list"), mappedStatement);
        }
        // TODO 扩充
    }

    private void fillCollection(Collection parameter, MappedStatement mappedStatement) {
        for (Object item : parameter) {
            if (item instanceof BaseEntity) {
                fillItem((BaseEntity)item, mappedStatement.getSqlCommandType());
            }
        }
    }
}
