---
title: Jmeter使用
order: 66
---

安装：

1. JAVA环境： JRE/JDK
1. 下载本体： <https://jmeter.apache.org/usermanual/component_reference.html>
1. 环境变量： `${JMETER_HOME}/bin`

概念：

+ 接口类型： HTTP/RPC/Websocket
+ 鉴权： Cookie/Session/Token/API-Key
+ JMX： 测试脚本
  + 组件：
    + 核心组件【必选】
      + 测试计划（Test Plan） —— 容器
      + 线程组（Thread Group） —— 工作任务的设置
      + 取样器（Sampler） —— 工作内容的设置
        + HTTP请求
    + 辅助组件【可选】
      + 前置处理器（Pre-Processor） —— **取样器**之前，自动执行
      + 后置处理器（Post-Processor） —— **取样器**之后，自动执行
      + 逻辑控制器（Logic Controller） —— 对**取样器**进行逻辑控制（条件、分支、循环、跳过）
      + 监听器（Listener） —— 展示**取样器**工作细节和结果
        + 查看结果树
      + 配置元件（Config Element） —— 配置修改**取样器**的设置
      + 定时器（Timer） —— 延迟**取样器**的执行
      + 断言（Assertion） —— 判断**取样器**的结果

元件加载顺序：

1. 加载配置元件
1. 加载线程组
1. 加载取样器
1. 为取样器加载辅助元件
    1. 父级组件
    1. 同级组件
    1. 子组件
    1. 注意：不会收集旁系组件

## 目录

```bash
backups/
bin/ ———————————————— 软件主体、配置文件
docs/ ——————————————— 开发文档
extras/ —————————————— 提供外部工具调用
lib/ ————————————————— 依赖库
licenses/
printable_docs/ —————— 使用说明
LICENSE
NOTICE
README.md
```

## 常见场景

### 自定义断言
### HTML测试报告
### 数据库查询
### Jenkins集成
### 性能测试

## 参考

+ b站|视频|jmeter接口测试 —— <https://www.bilibili.com/video/BV1us2sBKEv1>
