---
title: XML 介绍
date: 2024-04-14
tag:
  - xml
  - java
order: 1
---

介绍 Java 中操作 XML（Extensible Markup Language，可扩展标记语言） 的工具类。

<!-- more -->

## XML 简介

XML 是 W3C 制定的标准，被设计用来传输和存储数据。
XML 被设计为具有自我描述性，本身没有预定义标签。
我们使用时需要自行定义标签，所以很多使用 XML 作为配置的工具都有一套自己的 XML 标签语法（Schema）。

> **GML, SGML, HTML, XML, XHTML, HTML5**
>
> - GML 通用标记语言 v1
> - SGML 标准通用标记语言。描述了 DTD
> - HTML 一种 SGML 的应用
> - XML 一种 SGML 的应用
> - XHTML 基于 XML 的一种标记语言
> - HTML5 是基于浏览器的标准行为，而不再基于 SGML 的一种标记语言。为浏览器而设计。

```xml
HTML
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

HTML5
<!DOCTYPE html>

XML
<?xml version="1.0" encoding="UTF-8"?>

XHTML
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC
  "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
```

### 命名空间（namespace）

在 XML 中，元素名称由开发者定义。
当两个不同的文档使用相同的元素名时，就会发生命名冲突。
在同一份 XML 文档中也可能出现多个同名的标签和属性，而这些标签和属性又不完全相同。

e.g.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<root>
<!-- 上传 -->
<file>
  <path>http://example.org</path>
</file>
<!-- 下载 -->
<file>
  <path>./example.txt</path>
</file>
</root>
```

区分两个 file 需要用命名空间（`namespace`）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  xmlns（XML NameSpace）后加命名空间名
  等号（"="）后的路径无特殊要求，只需保证唯一即可。 —— 也就是说，也不一定需要为网络路径，只是业界习惯，方便规则查看
 -->
<root xmlns:upload="http://example.org/xxxupdate"> <!-- [!code highlight] -->
<!-- 上传 -->
<upload:file> <!-- [!code highlight] -->
  <path>http://example.org</path>
</upload:file>
<!-- 下载 -->
<download:file xmlns:download="http://example.org/xxxdownload"> <!-- [!code highlight] -->
  <path>./example.txt</path>
</download:file>
</root>
```

## DTD 介绍

DTD（文档类型定义）使用一系列的元素来定义文档的合法结构，可定义合法的 XML 文档构建模块。

```xml
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
```

### 引用 DTD

DTD 可被成行地声明于 XML 文档中，也可以作为一个外部引用。

内部使用：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html [
  <!ELEMENT root (#PCDATA)>
]>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"></html>
```

外部引用：（自定义）

::: tabs

@tab 引用 DTD 位置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!-- SYSTEM 引用本地系统文件 -->
<!DOCTYPE html SYSTEM "html.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"></html>
```

@tab 定义 DTD 位置

```xml title="html.dtd"
<!ELEMENT html (#PCDATA)>
```

:::

外部引用：（自定义）

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!--
  PUBLIC "描述" —— 引入一个公共文件，需要加上对应描述
  "描述" 格式： 组织//作者//描述//语言 （"-" 代表省略）
  e.g. ISO//Steven//just a test//ZH
-->
<!DOCTYPE html PUBLIC
  "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en"></html>
```

### 定义 DTD

- 元素
- 属性
- 实体
- PCDATA（parsed character data，被解析的字符数据） —— 兜底定义
- CDATA（character data，字符数据）

元素定义

::: tabs

@tab DTD 定义

```xml
<!-- ELEMENT 标签名 (子标签名) -->
<!ELEMENT class (student, teacher)> <!-- ❗定义了子标签名，那文档里就需要有这些标签的定义 -->
<!--
  1. 不确定子标签时，#PCDATA 用于兜底
  2. 声明次数
    ? 表示 0~1
    + 表示 1~n
    * 表示 0~n
  3. 互斥声明 (a|b)
-->
<!ELEMENT student (#PCDATA, (id|star), name+)>
<!ELEMENT id (#PCDATA)> <!-- 表示其内容为纯字符串 -->
<!ELEMENT teacher (#PCDATA, star, name*)>
<!ELEMENT star EMPTY> <!-- 表示单标签，无内部元素 -->
<!ELEMENT variable ANY> <!-- 可以使用文档中定义的任意 ELEMENT 作为子标签  -->

<!-- 混合写法（最常用） -->
<!ELEMENT student (#PCDATA|id|name)*>
```

@tab XML 例子

```xml
<class>
<student>
  <id>S001</id>
</student>
<teacher>
  <star/>
</teacher>
</class>
```

:::

属性定义

```xml
<!ELEMENT student>
<!ATTLIST student type CDATA> <!-- 默认 -->
<!ATTLIST student type (1|2) #REQUIRED> <!-- 要求 1 or 2 必须-->
<!ATTLIST student type 1 #FIXED> <!-- 值固定为 1 -->

<!ATTLIST student
type (1|2) #REQUIRED
name CDATA #FIXED "1">
```

实体定义（变量）

::: tabs

@tab DTD 定义

```xml
<!-- 数值定义 默认 & -->
<!ENTITY write "print">
<!ENTITY % xx "name">
<!ELEMENT student (%xx;)>
<!ELEMENT name (#PCDATA)>
```

@tab XML 使用

```xml
&write;
```

:::

## Java 操作 XML

Java 操作 XML 的方向有两个：

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
