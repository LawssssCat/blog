---
title: Thrift 介绍
date: 2024-04-05
tag:
  - thrift
order: 1
---

[Thrift](https://thrift.apache.org/) 是一个轻量级、跨语言的 RPC（Remote Procedure Call，远程过程调用） 框架，主要用于各个服务之间的 RPC 通信。

Thrift 已经在很多开源项目中被验证时稳定和高效的，例如：Cassandra, Hadoop, HBase 等。公司级的应用有 Facebook, 百度, 小米, 美团, 饿了么等。

<!-- more -->

Thrift 通过自身的中间语言 IDL（接口描述语言） 和代码生成引擎生成各种主流语言的 RPC 服务端/客户端模板代码。
Thrift 支持多种不同的编程语言，包括 C++, Java, Python, Ruby, Erlang, Haskell, C#, Cocoa, JavaScript, Node.js, Smalltalk, OCaml, Golang 等。

Thrift 技术栈分层：

- **传输层（Transport Layer）** —— 负责直接从网络中读取和写入数据。定义了具体的网络协议，比如 TCP/IP 传输等。
- **协议层（Protocol Layer）** —— 定义了数据传输格式，负责网络传输数据的序列化和反序列化，比如 json、XML、二进制数据等
- **处理层（Processor Layer）** —— 由具体的 IDL（接口描述语言） 生成。

## Thrift 的协议

Thrift 可以让用户选择客户端与服务端之间传输通信协议的类别。
在传输协议上总体划分为文本（text）和二进制（binary）协议。为节约带宽，提高传输效率，一般情况下使用二进制类型的传输协议。

常用的协议有以下几种：

- TBinaryProtocol —— 二进制编码格式进行数据传输
- TCompactProtocol —— 高效、密集的二进制编码格式进行数据传输
- TJSONProtocol —— 使用 JSON 文本的数据编码协议进行数据传输
- TSimpleJSONProtocol —— 只提供 JSON 只写的协议，适用于通过脚本语言解析

## Thrift 的传输层

常用的传输层有：

- TSocket —— 使用阻塞方式 I/O 进行传输。（最常见的传输模式） `bio`
- TNonblockingTransport —— 非阻塞方式，用于构建异步客户端
- TFramedTransport —— 非阻塞方式，按快的大小进行传输。`NIO`
