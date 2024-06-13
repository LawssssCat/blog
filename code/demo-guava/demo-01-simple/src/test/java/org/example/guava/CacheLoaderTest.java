package org.example.guava;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheLoaderTest {
    @Test
    void testCacheSimple() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(10) // 最大缓存数量
                // 过期
                // .expireAfterAccess(30, TimeUnit.SECONDS) // 缓存过期时间
                // .expireAfterWrite(30, TimeUnit.SECONDS) // 缓存写入后过期时间
                // .refreshAfterWrite(30, TimeUnit.SECONDS) // 缓存写入后刷新
                // 引用
                // .softValues() // 软引用（full gc）
                // .weakValues() // 弱引用（major gc + full gc）
                .removalListener(new RemovalListener<String, String>() {
                    @Override
                    public void onRemoval(RemovalNotification<String, String> notification) {
                        log.info("-----------> remove key: {}", notification.getKey());
                    }
                })
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        log.info("<----------- load key: {}", key);
                        return "cache-" + key;
                    }

                    //
                    @Override
                    public Map<String, String> loadAll(Iterable<? extends String> keys) throws Exception {
                        log.info("<----------- load all keys: {}", keys);
                        return super.loadAll(keys);
                    }
                });

        List<String> keys = Lists.newArrayList();
        for (int i = 0; i < 100; i++) {
            String key = "key-" + i;
            keys.add(key);
        }

        log.info("================== load one");
        keys.forEach(key -> {
            Assertions.assertNull(cache.getIfPresent(key)); // null cause non cache
            // cache.get(key); // load
            String one = cache.getUnchecked(key);// load and cache and do not throw checked exception
            // cache.get(key); // cache
            cache.getUnchecked(key); // load from cache
        });

        log.info("================== load all");
        try {
            ImmutableMap<String, String> all = cache.getAll(keys);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

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
}
