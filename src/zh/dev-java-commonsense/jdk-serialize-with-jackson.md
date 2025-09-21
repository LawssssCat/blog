---
title: JDK 序列化/反序列化功能（jackson）
date: 2024-08-07
tag:
  - java
  - json
order: 66
---

Java 对象和 JSON 数据之间进行转换。

核心概念、基本用法、高级配置及处理集合类型的数据。

参考：

- 原理、使用与高级配置 - https://blog.csdn.net/qq_38411796/article/details/139962039
- https://andriifedorenko.medium.com/the-perfect-jackson-objectmapper-ac10b8bf8764

```xml title="pom.xml"
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.12.3</version>
</dependency>
```

```java
ObjectMapper objectMapper = new ObjectMapper();

// 创建一个对象
User user = new User();
user.setName("John");
user.setAge(30);

// 序列化：Java 对象 -> JSON 字符串
String jsonString = objectMapper.writeValueAsString(user);
System.out.println("JSON String: " + jsonString);

// 反序列化：JSON 字符串 -> Java 对象
User deserializedUser = objectMapper.readValue(jsonString, User.class);
System.out.println("Deserialized User: " + deserializedUser);
```

## 概念

### ObjectMapper

ObjectMapper 核心类。
负责将 Java 对象转换为 JSON 以及将 JSON 转换为 Java 对象。
提供许多方法，例如 writeValue、readValue 等。

### JsonParser 和 JsonGenerator

- JsonParser：低级别的 JSON 解析器，用于逐步解析 JSON 内容。
- JsonGenerator：低级别的 JSON 生成器，用于逐步生成 JSON 内容。

### Annotations

Jackson 提供了许多注解来定制序列化和反序列化过程，包括但不限于：

- `@JsonProperty`: 指定字段的 JSON 名称。
- `@JsonIgnore`: 忽略字段。
- `@JsonFormat`: 格式化日期和时间。
- `@JsonInclude`: 指定包含的条件。

```java
@Data
class User {
    @JsonProperty("full_name")
    private String name;

    @JsonIgnore
    private int age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date birthDate;
}
```
