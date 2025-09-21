---
title: GDB使用
order: 66
---

GDB是调试工具，支持C/C++、Go、Java、....
GDB只有命令行界面，很多其他调试工具如VS提供了上层的GUI界面。

<!-- more -->

参考：

+ 【Linux】GDB保姆级调试指南（什么是GDB？GDB如何使用？） <https://blog.csdn.net/weixin_45031801/article/details/134399664>

todo 命令详情

## Debuginfod

参考：

+ <https://fedoraproject.org/wiki/Debuginfod>

```bash
export DEBUGINFOD_URLS=https://debuginfod.stg.fedoraproject.org/
export DEBUGINFOD_URLS=https://debuginfod.fedoraproject.org/
```
