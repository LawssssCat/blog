package org.example.cache.context;

import java.util.concurrent.TimeUnit;

/**
 * spring 工具类
 */
public interface CacheContext {
    void setObject(String key, Object value, int expireTime, TimeUnit timeUnit);

    void setObject(String key, Object value);

    <T> T getObject(String key, Class<T> clazz);
}
