package org.example.cache.context;

import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.example.common.utils.DateUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.fastjson2.JSONObject;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO 自动清除
 */
@Slf4j
public class H2CacheContext implements CacheContext {
    private static final String SQL_INIT = "drop table if exists mock_cache;"
        + "create table mock_cache ("
        + "    cache_key       varchar  not null comment '缓存主键',"
        + "    cache_value     varchar  not null comment '缓存数据',"
        + "    expire_time     datetime comment '更新时间',"
        + "    primary key(cache_key)"
        + ");"
        + "comment on table mock_cache is 'mock 缓存';"
        + "insert into mock_cache values ('1', '{}', CURRENT_TIMESTAMP());"
        + "insert into mock_cache values ('2', '{}', CURRENT_TIMESTAMP());";

    private static final String SQL_UPDATE = "insert into mock_cache (" +
        "cache_key," +
        "cache_value," +
        "expire_time" +
        ") values (?,?,?) ON DUPLICATE KEY UPDATE " +
        "cache_value = values(cache_value)," +
        "expire_time = values(expire_time)";

    private static final String SQL_SELECT = "select * from mock_cache where cache_key = ? and expire_time > ?";

    private JdbcTemplate jdbcTemplate;

    public H2CacheContext() throws Exception {
        // init datasource
        Properties properties = new Properties();
        properties.setProperty("url", "jdbc:h2:file:./db/cache;mode=MySQL");
        properties.setProperty("driverClassName", "org.h2.Driver");
        properties.setProperty("validationQuery", "select 1");
        DataSource dataSource = DruidDataSourceFactory.createDataSource(properties);
        jdbcTemplate = new JdbcTemplate(dataSource);
        log.info("cache h2 datasource: {}", dataSource);
        jdbcTemplate.execute(SQL_INIT);
        log.info("cache h2 init ok");
    }

    @Override
    public void setObject(String key, Object value, int expireTime, TimeUnit timeUnit) {
        value = JSONObject.toJSONString(value);
        Date expire;
        if (expireTime > -1) {
            expire = DateUtils.calcDate(DateUtils.getNowDate(), expireTime, timeUnit);
        } else {
            expire = new Date(1);
        }
        jdbcTemplate.update(SQL_UPDATE, key, value, expire);
    }

    @Override
    public void setObject(String key, Object value) {
        setObject(key, value, -1, TimeUnit.MILLISECONDS);
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(SQL_SELECT, key, DateUtils.getNowDate());
        if (sqlRowSet.next()) {
            String cacheValue = sqlRowSet.getString("cache_value".toUpperCase());
            return JSONObject.parseObject(cacheValue, clazz);
        }
        return null;
    }
}
