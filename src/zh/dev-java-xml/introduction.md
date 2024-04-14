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

- jaxp（Java API XML Processing） —— 关注如何找到某个 XML 元素内容
- jaxb（归属 `javax.xml.bind` 包） —— 关注如何把 XML 元素转换成 Java 对象，和关注如何把 Java 对象转换成 XML 元素

| 三方件                                    | 读  | 写  | 应用                     |
| ----------------------------------------- | --- | --- | ------------------------ |
| dom （`org.w3c.dom`） / xpath             | ✅  | ✅  | Mybatis:XMLConfigBuilder |
| sax（Simple API for XML）                 | ✅  | ❌  |
| degister                                  | ✅  | ❌  | Tomcat                   |
| dom4j                                     | ✅  | ✅  |
| jdom                                      | ✅  | ✅  |
| xstream                                   | ✅  | ✅  |
| jaxb（Java Architecture for XML Binding） | ✅  | ✅  |
| spring oxm（Object XML Mapping）          | ✅  | ✅  |

---

参考：

- B 站 | 乐之者 java | 各种 java 操作 xml 的方法 - https://www.bilibili.com/video/BV15T4y1P7nM
