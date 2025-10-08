---
title: 数组（Array）
order: 11
---

## 滑动窗口

[LeetCode 209 长度最小的子数组](https://leetcode.cn/problems/minimum-size-subarray-sum/description/)

## KMP 算法

- [LeedCode: 28. 找出字符串中第一个匹配项的下标](https://leetcode.cn/problems/find-the-index-of-the-first-occurrence-in-a-string/description/) （KMP 特解）
- [LeetCode：459.重复的子字符串](https://leetcode.cn/problems/repeated-substring-pattern/description/) （KMP 特解） todo

解决字符串匹配问题： indexOf

### 理论

参考：

1. 手工 https://www.bilibili.com/video/BV1UN4y1u7Nx/
1. 代码 https://www.bilibili.com/video/BV1HT411V71d/

关键：前缀表（next） —— 找到已匹配的前缀长度

前缀例子：

```bash
字符串： aabaa
前缀：
a
aa <--
aab
aaba
后缀：
a
aa <--
baa
abaa

字符串： aabaaf
前缀： x--
a
aa
aab
aaba
aabaa
后缀： x--
f
af
aaf
baaf
abaaf
```

关键：生成前缀表 —— 求最长相等前缀、后缀

```bash
字符串： aabaaf
最长相等前后缀： 片段 -> 子段 -> 前缀 -> 后缀 -> 长度
a -> null -> -1 -> -1 ->  -1 （无字段）
aa -> a -> null -> null -> 0 （无前缀/后缀）
aab -> aa -> a -> a -> 1
aaba -> aab -> aab -> aab -> 0 （不包含全段）
aabaa -> aaba -> a -> a -> 1
aabaaf -> aabaa -> aa -> aa -> 2
前缀表：
-1 0 1 0 1 2

例子2
ababac
-1 0 0 1 2 3
```

### 代码

```java
<!-- @include: @project/code/demo-java-algorithm/demo-datastructure-array/src/test/java/org/example/string/KMSTest.java -->
```
