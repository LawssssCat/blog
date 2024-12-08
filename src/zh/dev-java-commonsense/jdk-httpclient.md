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

架构：

- HttpClient/HttpClientBuilder/ClosableHttpClient.execute —— 门面 —— 提供各种功能的配置
  - MinimalHttpClient —— 最小集实现
    - requestExecutor
    - connManager
  - InternalHttpClient —— 完全可配置版本，提供丰富完善的功能。 （它不是 Public 的，智能通过 HttpClientBuilder 生成）
    - defaultConfig
    - routePlanner
    - execChain
    - connManager
    - cookieStore/cookieSpecRegistry
    - credentialsProvider
- ConnPool/CPool —— 连接管理器/连接池 —— 管理连接的生命周期。连接在连接池中创建、复用、移除。
  - 连接生命周期
    - close
    - connect —— 过程状态。调用连接时，如果不是 open 状态，就会进行连接。连接过程基于不同 schema （主要是 http 和 https）创建不同的 socket 连接并且将连接绑定到 socket 上。
    - open
    - stale —— 连接会因为心跳或者过期等原因被 close 变成 stale 状态，直到被复用或者连接池满时被清理
  - 实现
    - BasicHttpClientConnectionManager —— 基本实现，不是 “池化” 实现。一般不使用
    - PoolingHttpClientConnectionManager —— 池化实现 `this.pool.lease(route, state, null)`
- ClientExecChain/ProtocolExec/RetryExec/... —— 扩展器/执行器
- HttpRequestInterceptor —— 拦截器

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
