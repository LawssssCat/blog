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

外观设置：<https://www.youtube.com/watch?v=pw5DYaj65xo>

+ 平滑屏幕字体边缘
+ 显示缩略图，而不是显示图标
+ 在桌面上为图标标签使用阴影

winhance —— 卸载系统自带程序

+ People —— 联系人
+ MSN Weather —— 天气
+ Xbox ... —— 游戏
+ Microsoft News —— 新闻
+ 3D Viewer
+ Clipchamp —— 视频编辑
+ Alarms & Clock —— 闹钟
+ Get Help
+ Tips
+ Solitaire Collection —— 纸牌游戏
+ Quick Assis —— 远程协助
+ OneDrive —— 网盘
+ Copilot —— 弱智助手
+ MS 365 Copilot (Office Hub)
+ Bing Search —— 搜索
+ Recall —— 定时截图桌面？？？
+ ...
