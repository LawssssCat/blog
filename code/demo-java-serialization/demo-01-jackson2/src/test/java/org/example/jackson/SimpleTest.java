package org.example.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidDefinitionException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.AllArgsUser;
import org.example.entity.JsonCreatorUser;
import org.example.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@Slf4j
public class SimpleTest {
    ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    void test() {
        // 创建一个对象
        User user = new User();
        user.setName("John");
        user.setAge(30);

        // 序列化：Java 对象 -> JSON 字符串
        String jsonString = objectMapper.writeValueAsString(user);
        log.info("JSON String: {}", jsonString);

        // 反序列化：JSON 字符串 -> Java 对象
        User deserializedUser = objectMapper.readValue(jsonString, User.class);
        log.info("Deserialized User: {}", deserializedUser);
    }

    /**
     * 前提：
     * 1. 基于 Java8+ 编译
     * 2. 开启了 -parameters 编译参数
     *
     * 参考： https://github.com/FasterXML/jackson?tab=readme-ov-file
     * 参考： jackson-module-parameter-names
     */
    @SneakyThrows
    @Test
    void testAllArgs() {
        // 创建一个对象
        AllArgsUser user = new AllArgsUser("John", 30);

        // 序列化：Java 对象 -> JSON 字符串
        String jsonString = objectMapper.writeValueAsString(user);
        log.info("JSON String: {}", jsonString);

        // 反序列化：JSON 字符串 -> Java 对象
        // 💡需要开启 maven -> build -> plugin -> maven-compiler-plugin, <parameters>true</parameters>
        Assertions.assertThrowsExactly(InvalidDefinitionException.class, () -> {
            // Cannot construct instance of `org.example.entity.AllArgsUser` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
            // at [Source: (String)"{"name":"John","age":30}"; line: 1, column: 2]
            AllArgsUser deserializedUser = objectMapper.readValue(jsonString, AllArgsUser.class);
            log.info("Deserialized User: {}", deserializedUser);
        });
    }

    @SneakyThrows
    @Test
    void testJsonCreator() {
        // 创建一个对象
        JsonCreatorUser user = new JsonCreatorUser("John", 30);

        // 序列化：Java 对象 -> JSON 字符串
        String jsonString = objectMapper.writeValueAsString(user);
        log.info("JSON String: {}", jsonString);

        // 反序列化：JSON 字符串 -> Java 对象
        JsonCreatorUser deserializedUser = objectMapper.readValue(jsonString, JsonCreatorUser.class);
        log.info("Deserialized User: {}", deserializedUser);
    }
}
