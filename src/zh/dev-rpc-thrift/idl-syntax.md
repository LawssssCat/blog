---
title: IDL 语法
date: 2024-04-05
tags:
  - thrift
order: 2
---

Thrift 是一个典型的 CS（客户端/服务端） 结构，客户端和服务端可以使用不同的语言开发，通过 IDL（Interface Description Language） 作为中间语言来关联客户端和服务端的语言。

Thrift 采用 IDL（Interface Description Language） 来定义通用的服务接口，然后通过 Thrift 提供的编译器，将服务接口编译成不同语言编写的代码，通过这个方式来实现跨语言的功能。

<!-- more -->

## 语法

### 数据类型（type）

<https://thrift.apache.org/docs/types.html>

| type       | desc                 | java                | go       |
| ---------- | -------------------- | ------------------- | -------- |
| i8         | 有符号，8 位整数     | byte                | int8     |
| i16        | 有符号，16 位整数    | float               | int16    |
| i32        | 有符号，32 位整数    | int                 | int32    |
| i64        | 有符号，64 位整数    | long                | int64    |
| double     | 64 位浮点数          | double              | float64  |
| bool       | 布尔值               | boolean             | bool     |
| string     | 文本字符串（UTF-8）  | java.lang.String    | string   |
| binary     | 未编码的字节序列     | java.nio.ByteBuffer | []byte   |
| list\<T\>  | 有序列表，允许重复   | java.util.List      | []T      |
| set\<T\>   | 无序列表，不允许重复 | java.util.Set       | []T      |
| map\<K,V\> | 字典                 | java.util.Map       | map[K] V |

```go
// 结构体
struct Test {
  1: map<string, User> usermap,
  2: set<i32> intset
  3: list<double> list
}
```

### 常量（const）

```go
// 常量
const i32 MALE_INT = 1
const map<i32, string> GENDER_MAP = {1: "male", 2: "female"}
// 别名
typedef map<i32, string> gmp
```

### 结构体（struct）

```go
struct <结构体名称> {
  <序号>:[字段性质] <字段类型> <字段名称> [=<默认值>] [;|,]
}

e.g.
struct User {
  1: required string name, // required 必须填充，必须序列化
  2: optional i32 age = 0; // optional 不填充则不序列化
  3: bool gender // 默认 optional
}
struct bean {
  1: i32 number = 10,
  2: i64 bigNumber,
  3: double decimals,
  4: string name = "thrifty"
}
```

### 枚举（enum）

Thrift 不支持枚举嵌套，且常量必须是 32 位正整数。

```go
enum HttpStatus {
  OK = 200,
  NOTFOUND = 404
}
```

### 异常（exception）

```go
exception MyException {
  1: i32 errorCode
  2: string message
}

service ExcampleService {
  string GetName() throws (1: MyException e)
}
```

### 服务（service）

服务的定义方法在语义上等同于面向对象语言中的接口。

```go
service HelloService {
  i32 sayInt(1: i32 param)
  string sayString(1:string param)
  bool sayBoolean(1:bool param)
  void sayvoid()
}
```

### 命名空间（namespace）

类似于 C++ 中的 namespace， java 中的 package， python 中的 module。

```go
namespace java com.example.test
```

### 包含（include）

用于管理、重用和提高模块性/组织性。

```go
include "test.thrift"

struct StSearchResult {
  1: int3 uid;
  ...
}
```

### 注释（comment）

```go
/**
 * This is a multi-line comment.
 */
// single-line comment.
```
