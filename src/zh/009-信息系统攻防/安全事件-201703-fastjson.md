---
title: 时间线分析fastjson漏洞利用
---

> 参考
>
> - [阿里云漏洞库 | 关于「fastjson」的漏洞数据](https://avd.aliyun.com/product?prod=fastjson)
> - <https://su18.org/post/fastjson/>

<!-- more -->

## fastjson-1.2.24

### [CVE-2017-18349 Auto Type RCE](./vulnerability/CVE-2017-18349.md)

在2017年3月15日，fastjson官方主动爆出在 1.2.24 及之前版本存在远程代码执行高危安全漏洞：
“**fastjson 默认使用 `@type` 指定反序列化任意类**，攻击者可以通过在 Java 常见环境中寻找能够构造恶意类的方法，通过反序列化的过程中调用的 getter/setter 方法，以及目标成员变量的注入来达到传参的目的，最终形成恶意调用链。”

历史地位：
此漏洞开启了 fastjson 反序列化漏洞的大门，为安全研究人员提供了新的思路。

## fastjson-1.2.25

官方对之前的反序列化漏洞进行了修复：
引入了 checkAutoType 安全机制，默认情况下 autoTypeSupport 关闭，不能直接反序列化任意类。而打开 AutoType 之后，是基于内置黑名单来实现安全的，fastjson 也提供了添加黑名单的接口。

清理了一部分，但是保留了一部分：
**由于存在逻辑漏洞，导致 checkAutoType 安全机制可以被绕过！**
