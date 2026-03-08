package org.example.guava;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
public class CacheLoaderTest {
    /**
     * 自动缓存加载器基本功能测试
     * <br>
     * expire ... 过期策略<br>
     * reference ... 内存策略<br>
     * remove listen ... 过期监听<br>
     * load one / load all ... <br>
     */
    @DisplayName("LocalLoadingCache 基本功能测试，如：过期策略、内存策略、过期监听等")
    @Test
    void testLocalLoadingCache() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                // 基于 ConcurrentHashMap 实现，所以可以设置该类初始化参数
                .initialCapacity(2) // 初始容量
                .concurrencyLevel(8) // 并发级别 —— 通过配置 concurrencyLevel，来控制 segment 的数量，来提高并发。ConcurrentHashMap 的默认值是 16，最大值是 1<<16；这里的默认值是 4，最大值为 1<<16。
                // 过期（非互斥，可同时设置多个）
                .maximumSize(10) // 最大缓存数量
                // .maximumWeight(60) // 最大缓存权重和
                // .weigher(...) // 权重计算器，设置 maximumWeight 后必须设置 weigher
                // .expireAfterAccess(30, TimeUnit.SECONDS) // 缓存过期时间
                // .expireAfterWrite(30, TimeUnit.SECONDS) // 缓存写入后过期时间
                // .refreshAfterWrite(30, TimeUnit.SECONDS) // 缓存写入后刷新
                // 过期监听
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        String cause = "";
                        if (RemovalCause.EXPLICIT.equals(notification.getCause())) {
                            cause = "被显式移除";
                        } else if (RemovalCause.REPLACED.equals(notification.getCause())) {
                            cause = "被替换";
                        } else if (RemovalCause.EXPIRED.equals(notification.getCause())) {
                            cause = "被过期移除";
                        } else if (RemovalCause.SIZE.equals(notification.getCause())) {
                            cause = "被缓存条数超上限移除";
                        } else if (RemovalCause.COLLECTED.equals(notification.getCause())) {
                            cause = "被垃圾回收移除";
                        }
                        log.debug("-----------> remove key \"{}\" cause \"{}({})\"", notification.getKey(), cause, notification.getCause());
                    }
                })
                // 引用
                // .softValues() // 软引用（full gc）
                // .weakValues() // 弱引用（major gc + full gc）
                // 统计（hitCount=6, missCount=3, loadSuccessCount=3, loadExceptionCount=0, totalLoadTime=3208400, evictionCount=10）
                .recordStats() // 开启统计信息记录，通过 cache.stats() 获取
                .build(new CacheLoader<String, String>() { // 获得 LocalLoading
                    //
                    @Override
                    public String load(String key) throws Exception {
                        log.debug("<----------- load key: {}", key);
                        return "cache-" + key;
                    }

                    //
                    @Override
                    public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
                        log.debug("<----------- load all keys: {}", keys);
                        return super.loadAll(keys);
                    }
                });

        log.info("================== put manually");
        cache.put("key-put-1", "test"); // 手动放入缓存
        cache.putAll(new HashMap<>());

        log.info("================== remove manually");
        cache.invalidate("key-put-1");
        cache.invalidateAll();

        log.info("================== loading if miss");
        List<String> keys = Lists.newArrayList(); // 准备 load key
        for (int i = 0; i < 100; i++) {
            String key = "key-" + i;
            keys.add(key);
        }

        /*
         * LoadingCache.get（k） ->  CacheLoader.load(k)
         * LoadingCache.refresh(k) ->   CacheLoader.reload(k)
         * LoadingCache.getAll(keys) -> CacheLoader.loadAll(keys)
         */
        log.info("================== - load one");
        keys.forEach(key -> {
            // null cause non cache
            Assertions.assertNull(cache.getIfPresent(key)); // 💡第一次获取，没有数据
            // load and cache and do not throw checked exception
            // cache.get(key); // load
            String one = cache.getUnchecked(key); // 💡获取次数超过缓存最大数量后，触发 remove listen 回调
            // load from cache
            // cache.get(key); // cache
            cache.getUnchecked(key); // 💡已缓存：再次获取，没有调用 load 方法
        });

        log.info("================== - load all");
        try {
            ImmutableMap<String, String> all = cache.getAll(keys); // 💡调用 loadAll 方法
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("================== stats");
        log.debug("stats: {}", cache.stats());
    }

    /**
     * 问题处理：返回 null 时，抛出异常的处理
     */
    @DisplayName("CacheLoader 测试：返回 null 问题")
    @Test
    void testCacheLoad_null() {
        {
            LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                    .build(new CacheLoader<String, String>() {
                        @Override
                        public String load(String key) throws Exception {
                            log.info("<----------- load key: {}", key);
                            return "key-null".equals(key) ? null : "cache-" + key;
                        }
                    });

            // 抛出异常，因为不允许返回null
            Assertions.assertThrowsExactly(CacheLoader.InvalidCacheLoadException.class, () -> {
                cache.getUnchecked("key-null");
            });
        }

        // 用 optional 处理
        {
            LoadingCache<String, Optional<String>> cache = CacheBuilder.newBuilder()
                    .build(new CacheLoader<String, Optional<String>>() {
                        @Override
                        public Optional<String> load(String key) throws Exception {
                            log.info("<----------- load key: {}", key);
                            return "key-null".equals(key) ? Optional.empty() : Optional.of("cache-" + key);
                        }
                    });

            Optional<String> one = cache.getUnchecked("key-null");
            Assertions.assertFalse(one.isPresent());
        }
    }

    @Test
    void testSpec() {
        // todo 缓存参数文件配置
    }
}
