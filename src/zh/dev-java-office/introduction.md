---
title: Java Excel 操作
date: 2024-07-02
tag:
  - java
  - office
  - excel
order: 1
---

> 参考：
>
> - https://www.cnblogs.com/Chary/p/18112420

## 各版本 Excel 区别

不同版本的 Excel 在功能和格式上可能会有一些差异。所以后续在处理不同版本的 excel 时，会有少许不同

以下是一些常见的 Excel 版本之间的区别

1. Excel 97-2003（.xls）

   - 最大行数为 65536 行，最大列数为 256 列。
   - 支持的最大单元格格式有限。
   - 不支持新的 Excel 特性，如条件格式、表格样式等。
   - 文件大小限制为 2GB。

2. Excel 2007 及以上版本（.xlsx）

   - 最大行数和列数均有较大提升，支持数百万行数和 16384 列。
   - 支持更多的单元格格式和样式。
   - 引入了新的功能，如条件格式、表格样式、数据透视表等。
   - 支持更多的图表类型和图表样式。
   - 文件大小限制较大，最多可达 16,384 x 1,048,576 个单元格

## Excel 基本结构介绍

Java 是面向对象的操作语言，万物皆对象。了解了 Excel 基本结构有助于我们将 Excel 与 Java 中对象关联起来

- 工作簿（Workbook）： Excel 文件以工作簿的形式存在，一个工作簿可以包含多个工作表（Sheet）
- 工作表（Sheet）： 每个工作表由行（Row）和列（Column）组成，交叉形成单元格（Cell），用于存储数据、文本、公式等内容
- 单元格（Cell）： Excel 中的最小单位，用于存储数据或公式。每个单元格有一个唯一的地址，例如 A1、B2 等
- 行（Row）和列（Column）： 行是水平方向的一组单元格，列是垂直方向的一组单元格。行用数字标识，列用字母标识
- 公式（Formula）： Excel 支持使用公式进行计算和数据处理。公式以等号（=）开头，可以引用其他单元格的数值或内容进行运算
- 函数（Function）： Excel 提供了大量的内置函数，用于进行各种复杂的计算和数据处理，如 SUM（求和）、AVERAGE（平均值）、VLOOKUP（垂直查找）

![image.png](https://s2.loli.net/2024/07/02/kP7mKptglZfN964.png)