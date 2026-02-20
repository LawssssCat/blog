---
title: Linux 笔记
order: 1
---

操作系统 = 内核 + 发行版本

桌面操作系统 = 内核 + 发行版本 + 图形架构（图像子系统 + 桌面环境）

内核包括：

+ 文件系统
  + 系统将所有资源（硬件、状态、数据）抽象为可通过路径访问的“**文件**”，统一了访问接口。
  + **硬件**（CPU、内存等硬件设备）、**配置**（系统配置参数）、**状态**（运行时状态）在系统层面都被视为文件对象。
  + 对资源的操作被简化为**统一的标准文件I/O操作**，即打开、读取、写入和关闭。
  + 将模糊的“系统管理问题”转化为明确的“文件定位于操作问题”
+ 权限系统
  + 权限三元组：所有者（Owner）、所属组（Group）、其他人（Owner）
  + 操作三元组：读（Read）、写（Write）、执行（Execute）
+ 插件系统
  + Linux生态的核心在于提供基础工具，并赋予用户自由组合的能力。
  + 每个工具只做好一件事，功能专注且边界清晰，确保组件的高可靠性。
  + 通过标准化接口协同工作，简单工具的灵活串联能解决复杂的系统问题。
  + 面对复杂需求时，需拆解问题寻找组合方案，而非执着寻找“万能按钮”。
+ 配置文件系统（非物理系统，一般由发行方通过Shell脚本组织）
  + 系统级 - 需管理员权限，风险较高
    + `/etc`
    + `/etc/nginx`
    + `/etc/systemd`
    + `/etc/X11`
  + 用户级 - 需用户权限，风险可控
    + `~`
    + `~/.config`
    + `~/.config/fish`
    + `~/.config/gtk-3.0`
    + `~/.local/share`
    + `~/.local/share/applications`
    + `~/.local/share/flatpak`

图形架构：

+ 应用 —— 终端、浏览器等有UI的软件
+ 桌面环境 —— GNOME/KDE
+ 图形子系统 —— X11/Wayland
+ 驱动 —— 处理硬件交互
+ 硬件 —— 显示器、键盘、鼠标

---

内核：

+ Unix 内核
+ Linux 内核

发行版：

+ ???
  + ~~[Centos](./redhat/README.md)~~
  + Fedora 【桌面】
  + RHEL
  + Kail
+ Debian
  + [ubuntu](./ubuntu/README.md) 【桌面】
+ Rocky Linux
+ suse
+ arch 【桌面】
+ Linux Mint
+ tails
+ Zorin OS

+ [openwrt](./openwrt/README.md)

桌面环境：

+ GNOME
+ KDE

图形系统：

特性维度 | X11（传统显示协议） | Wayland（现代显示协议）
--- | --- | ---
设计模式 | 中央总机模式，中转所有请求，效率较低 | 直连对讲模式，客户端直接与内核通信
安全性 | 较低，应用间缺乏隔离，可相互窥探 | 高，应用被严格沙箱隔离，更安全
显示效果 | 高分屏缩放实现复杂，效果欠佳 | 原生支持任意倍率缩放及HDR显示
现状 | 逐步淘汰的旧标准，仅用于兼容 | 主流发行版默认选项，未来的趋势

包管理与软件生态：Linux没有“唯一应用商店”，而是由发行版、官方及第三方共同构成的灵活生态。

+ 发行版官方仓库 —— 最稳定安全的选择，但软件版本通常不是最新。
  + apt（DEB格式）
  + dnf/yum（[RPM格式](./package-rpm.md)）
+ 通用包格式 —— 跨发行版通用，沙箱（Sandbox）隔离更安全，但体积较大。
  （沙箱隔离机制时为了安全，但默认配置通常非常严格，限制了应用对系统资源如文件系统、硬件设备的访问访问，这会导致如钉钉无法访问下载目录以外文件、Stream无法访问麦克风设备等的问题。解决这类问题，可以使用FlatSeal等图形化工具手动配置权限，或者通过命令行参数调整访问策略。注意权衡便利性与安全性。）
  + Flatpak
  + Snap
+ 第三方仓库 —— 提供丰富软件，但需要注意来源安全性，建议慎用。
  + PPA
  + AUR

新闻：

+ Linux-Console.net <https://cn.linux-console.net/>

文件系统：

+ d `mkdir a`
+ p `mkfifo b` 管道
+ s `nc -lU c`
+ c `ll /dev/video0` 字设备（Character Devices），如摄像头、鼠标、键盘、... 参考 <https://doc.embedfire.com/linux/h618/driver/zh/latest/linux_driver/base/character_device/character_device.html>
+ b `ll /dev/dm-0` 块设备（Block），如硬盘、USB、...等无法直接字节写入写出，需要通过文件系统操作的设备。可以通过`lsblk`查看块设备情况。
