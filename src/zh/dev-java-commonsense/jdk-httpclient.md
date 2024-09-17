---
title: JDK 接口调用
date: 2024-09-12
tag:
  - rpc
order: 80
---

Java 远程接口调用方式

应用： 三方调用、爬虫（jsoup）

---

原生

- HttpURLConnection

三方工具

- okhttp —— android 用的多 | retrofit
- httpclient
- resttemplate —— Spring 封装的接口调用，底层调用 httpclient

## HttpURLConnection 使用

::: tabs

@tab 原生使用

```java
<!-- @include: @project/code/demo-java-common/demo-httpclient/src/test/java/org/example/HttpURLConnTest.java -->
```

@tab Mock 环境

```java
<!-- @include: @project/code/demo-java-common/demo-httpclient/src/test/java/org/example/BaseTest.java -->
```

:::

## HttpClient 使用

<https://hc.apache.org>

```java
<!-- @include: @project/code/demo-java-common/demo-httpclient/src/test/java/org/example/HttpClientTest.java -->
```

## RestTemplate 使用

### Get 请求

```java
public ResponseEntity<Object> test0(UserDTO userDTO) {
  Integer age = userDTO.getAge();
  String sex = userDTO.getSex();
  String url = "http://localhost:8080/test?age={1}&sex={2}";
  User user = restTemplate.getForObject(url, User.class, age, sex);
  // 相比使用 restemplate.getForObject()
  // 使用 restTemplate.getForEntity(String, Class<T>, Object...): ResponseEntity<T> 更好处理运行时异常
  return ResponseEntity.ok(user);
}
```

### Post 请求

```java
public ResponseEntity<Object> test2(UserDTO userDTO) {
  String url = "http://localhost:8080/user/test";
  HttpEntity<UserDTO> request = new HttpEntity<>(userDTO);
  User user = restTemplate.postForObject(url, request, User.class);
  return ResponseEntity.ok(user);
}
```

### 请求头参数

todo
