---
title: XPath 介绍
date: 2024-04-16
tag:
  - xml
  - xpath
order: 88
---

官方教程： <https://www.w3school.com.cn/xpath/index.asp>

XPath 是 W3C 的标准，被设计为供 XSLT, XPointer 以及其他 XML 解析软件使用，用于遍历、检索 XML 元素。
XPath 在 xml 解析、爬虫中经常被使用。

<!-- more -->

XPath 使用路径表达式来选取 XML 文档中的节点或节点集合。

包含一个标准函数库，内建 100 多个函数，包含：字符串值、数值、日期和时间的比较；节点和 QName 的处理、序列处理、逻辑值等。

<SiteInfo
  name="XPath 教程"
  url="https://zvon.org/xxl/XPathTutorial/General_chi/examples.html"
  preview="/assets/images/cover3.jpg"
/>

## XPath 节点

在 XPath 中，有 7 种类型的节点（Node）：

- 元素
- 属性
- 文本
- 命名空间
- 处理指令
- 解释
- 文档节点（即 “根节点”）

节点关系：

- 父（Parent）
- 子（Children）
- 同胞（Sibling）
- 先辈（Ancestor）
- 后代（Descendant）

## XPath 路径表达式

XPath 使用路径表达式在 XML 文档中选取节点。

| 表达式     | 描述                                                           |
| ---------- | -------------------------------------------------------------- |
| `nodename` | 选取此节点的所有子节点                                         |
| `/`        | 选取从根节点                                                   |
| `//`       | 选择的文档中的节点，不考虑它们的位置                           |
| `.`        | 选取当前节点                                                   |
| `..`       | 选取父节点                                                     |
| `@`        | 选取属性                                                       |
| `[]`       | 谓语（Predicates）：查找某个特定节点或者包含某个指定的值的节点 |
| `and`      | `//span[@price=66 and text()='苹果']`                          |
| `or`       | `//span[@price=66 or text()='苹果']`                           |
| `*`        | 匹配任何元素（element）节点                                    |
| `node()`   | 匹配任何类型的节点                                             |
| `@*`       | 匹配任何属性节点                                               |
| `\|`       | 合并两条表达式结果                                             |

::: tip
在浏览器中，使用 `$x("XPath语法")` 可以使用 XPath 路径表达式检索 HTML 页面。
:::

e.g.

```
bookstore
/bookstore
bookstore/book
//book
bookstore//book
//@lang

//div[@data-fill='fillData']/.. —— 选取文档中 div 属性为 data-fill 属性值为 fillData 的父节点
/bookstore/book[1] —— 第一个
/bookstore/book[last()] —— 最后一个
/bookstore/book[last()-1] —— 倒数第二个
/bookstore/book[position()<3] —— 选取前两个
//title[@lang]
//title[@lang='eng']
/bookstore/book[@price>35.00]
/bookstore/book[@price>35.00]/title
```

通过关系获取节点

| 表达式              | 描述                           |
| ------------------- | ------------------------------ |
| `child`             | 选取当前节点的所有子元素       |
| `ancestor`          | 选取当前节点的先辈             |
| `preceding-sibling` | 选取当前节点之前的所有同胞元素 |
| `following-sibling` | 选取当前节点之后的所有同胞元素 |

e.g.

```
/html//div/child::test()
/html//div[@class='测试']//ancestor::div
/html//div[@class='测试']//preceding-sibling::div
/html//div[@class='测试']//preceding-sibling::*
//span[@price='600']/following-sibling::*
```

XPath 函数

| 函数            | 描述                                    |
| --------------- | --------------------------------------- |
| `last()`        |
| `text()`        |
| `node()`        | 获取所有节点                            |
| `contains()`    | `//span[contains(@class, 'a')]`         |
| `starts-with()` | `//span[starts-with(@class, 'a')]`      |
| `not()`         | `//span[not(starts-with(@class, 'a'))]` |

## XSLT

> XPath 是 XSLT 中的主要元素。
> XSLT 是 XSL（Extensible Stylesheet Language，扩展样式语言）的转换，通过 XSLT 可以将 XML 转换为其他文档，如 XHTML。

todo

---

参考：

- https://www.bilibili.com/video/BV1zU4y1E7Ho/
