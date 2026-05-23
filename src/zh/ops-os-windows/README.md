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

## 安全问题

MSRC（Microsoft Security Response Center，微软安全响应中心）接收漏洞报告提供赏金。

260514爆出新瓜：安全人员[Nightmare Eclipse](https://gitlab.com/nightmare-eclipse)反馈漏洞，但未收到赏金，将未修复漏洞公开。（see [link_NightmareEclipse's_blog](https://deadeclipse666.blogspot.com/2026/05/were-doing-silent-patches-now-huh-also.html)）

+ YellowKey
+ RedSun —— CVE-2026-41091
+ BlueHammer —— CVE-2026-33825 （已修复）
+ UnDefend —— CVE-2026-45498
+ MiniPlasma
+ GreenPlasma

## 常见问题

todo b站 | 谈起哥 | WIndows 系统分析与应用 <https://www.bilibili.com/video/BV15u4y1N7sF>

### 忘记登陆密码

todo 解释 <https://www.bilibili.com/video/BV1xSGt6dE14/>

```bash2
cd windows
cd system32
del utilman.exe
copy cmd.exe utilman.exe
cd zh-cn
del utilman.exe.mui
copy cmd.exe.mui utilman.exe.mui

net user zdy ""
```

### 忘记BitLocker密码

#### TPM工具 （复杂）

todo <https://www.youtube.com/watch?v=wTl4vEednkQ>

#### YellowKey （简单）（范围：win11）

todo 解释

貌似只在WinRE（Windows Recovery Environment）上生效，但是恰好够用，真巧合。

相关：

+ YellowKey
  + ~~<https://github.com/Nightmare-Eclipse/YellowKey>~~ —— 原始作者，被删库了。
  （[link_新库](https://gitlab.com/nightmare-eclipse/YellowKey)）
    + <https://github.com/xiaoji235/bitlocker-bypass-tool-for-winre> —— fork
    + <https://github.com/0xDimas/YellowKey> —— fork
  + <https://github.com/LawssssCat/YellowKey-POC>
    + <https://github.com/LawssssCat/YellowKey-POC> —— fork

使用：

1. 进入WinPE
1. 插入u盘（要有NTFS/exFAT/FAT32分区）
1. 将 FsTx文件夹 移入u盘 “System Volume Information” 目录
1. 重启
1. 登陆界面按shift点重启
1. 可以通过notepad看到已经可以访问系统盘
