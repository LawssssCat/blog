---
title: Windows 使用
---

常用命令行：

```bash
# 打开当前路径的文件管理器
explore .
start .

# 环境变量
%USERPROFILE% —— 指向当前用户的主目录，通常是C:\Users\用户名。在这里可以找到用户的文档、桌面、下载等文件夹。
%APPDATA% —— 指向当前用户的应用数据文件夹，通常是C:\Users\用户名\AppData\Roaming。这个文件夹用于存储应用程序的配置文件和数据。
%LOCALAPPDATA% —— 指向本地应用数据文件夹，通常是C:\Users\用户名\AppData\Local。这个文件夹用于存储应用程序的本地数据，不会在不同设备间同步。
%DESKTOP% —— 指向用户的桌面文件夹，通常是C:\Users\用户名\Desktop。
%DOCUMENTS% —— 指向用户的文档文件夹，通常是C:\Users\用户名\Documents。
%DOWNLOAD% —— 指向用户的下载文件夹，通常是C:\Users\用户名\Downloads。
```

包管理：

```bash
# powertoys —— microsoft 提供的系统工作扩展
scoop install powertoys
```

wsl： [link](../ops-os-vm/wsl.md)

内核：

Windows 内核开发 Huo <https://www.bilibili.com/video/BV11JApeuE1V/>
