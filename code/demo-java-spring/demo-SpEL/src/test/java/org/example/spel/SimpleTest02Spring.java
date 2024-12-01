package org.example.spel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.util.Properties;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
public class SimpleTest02Spring {

    @Resource
    private Environment env;

    @Value("#{ '${test.spel.var1}' }")
    private String var1;
    @Value("#{ '${test.spel.var1}'.split(' ')[0] }")
    private String var1_1;
    @Value("#{ '${test.spel.var1}'.split(' ') }")
    private String[] var1_array;
    @Value("#{ '${test.spel.var1}'.split(' ') }")
    private Set<String> var1_set; // 💡自动适应

    @Test
    void testApplicationConfig() {
        assertEquals("hello world", var1);
        assertEquals("hello world", env.getProperty("test.spel.var1"));
        assertEquals("hello", var1_1);
        assertArrayEquals(new String[]{"hello", "world"}, var1_array);
        assertArrayEquals(new String[]{"hello", "world"}, var1_set.toArray());
    }

    private Properties yamlProperties = yamlConfigurer();

    @SneakyThrows
    private static Properties yamlConfigurer() {
        YamlPropertiesFactoryBean factory = new YamlPropertiesFactoryBean();
        factory.setResources(new ClassPathResource("test01.properties"));
        return factory.getObject();
    }

    /**
     * 通过 maven 控制 profile 加载配置
     */
    @Test
    void testMavenVariable() {
        String msg1 = yamlProperties.getProperty("msg1");
        log.info(msg1);
        assertEquals("dev", msg1);
    }
}
