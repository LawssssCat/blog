---
title: WireMock 使用
date: 2024-09-15
tag:
  - java
  - test
order: 33
---

Java 语言编写的 API 模拟工具

支持多种协议，可在单元测试中使用，也可单独部署

---

资料：

- Github <https://github.com/wiremock/wiremock>
- 文档 <https://wiremock.org/docs/>

基本使用

```java
<!-- @include: @project/code/demo-java-test/usage-wiremock/src/test/java/org/example/DeclarativeWireMockTest.java -->
```

Json 格式

::: tabs

@tab 测试用例

```java
<!-- @include: @project/code/demo-java-test/usage-wiremock/src/test/java/org/example/MappingWireMockTest.java -->
```

@tab Json 格式响应配置

```java
<!-- @include: @project/code/demo-java-test/usage-wiremock/src/test/resources/myWiremockFiles/mappings/t1.json -->
```

:::
