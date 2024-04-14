---
title: XML 操作（Java）
date: 2024-04-14
tag:
  - xml
  - java
order: 1
---

介绍 Java 中操作 XML 的工具类。

<!-- more -->

操作 XML 的方向有两个：

- jaxp（Java API XML Processing） —— 关注如何找到某个 XML 元素内容
- jaxb（Java Architecture for XML Binding，归属 `javax.xml.bind` 包） —— 关注如何将 XML 元素和对象映射起来。编组（marshaller）和反编组（unmarshaller）

具体实现：

| 实现    | 三方件                                    | 读  | 写  | 应用                     | 备注      |
| ------- | ----------------------------------------- | --- | --- | ------------------------ | --------- |
| 🟢 jaxp | dom（`org.w3c.dom`）, xpath               | ✅  | ✅  | Mybatis:XMLConfigBuilder | JDK 原生  |
| 🟢 jaxp | sax（Simple API for XML）                 | ✅  | ❌  |                          | 流操作    |
| 🟢 jaxp | dom4j                                     | ✅  | ✅  |
| 🟢 jaxp | jdom                                      | ✅  | ✅  |
| 🔵 jaxb | jaxb（Java Architecture for XML Binding） | ✅  | ✅  |                          | JDK 原生  |
| 🔵 jaxb | xstream                                   | ✅  | ✅  |
| 🔵 jaxb | apache digester                           | ✅  | ❌  | Tomcat                   | sax + oxm |
| 🔵 jaxb | spring oxm（Object XML Mapping）          | ✅  | ✅  |

---

参考：

- oracle | <https://docs.oracle.com/javase/8/docs/technotes/guides/xml/>
- B 站 | 乐之者 java | 各种 java 操作 xml 的方法 - https://www.bilibili.com/video/BV15T4y1P7nM
