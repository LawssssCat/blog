---
title: 游戏
tag:
  - life
---

## Switch

系统 （[入门](https://switch.hacks.guide/)、[源起](https://switch.hacks.guide/)）

- [hekate](https://github.com/CTCaer/hekate) —— 启动引导
  - Options/Auto Boot/ON —— 自动进入预设系统 （开机时，长按 "`-`" 进入引导系统）
- [atmosphere](https://github.com/Atmosphere-NX/Atmosphere) （ASM） —— 虚拟固件

- 整合包 （hekate + atmosphere + 自制软件）

  - ~~https://www.sdsetup.com/?p=/console&q=switch —— 自定义整合包~~ （已死）
  - [DeepSea](https://github.com/Team-Neptune/DeepSea) —— Neptune 团队
    - 用法：解压放入 TF 卡根目录，并将 `hekate_ctcaer_x.x.x.bin` 更名成 `payload.bin`
  - [sskyNS/RoastDuck-CFWPack](https://github.com/sskyNS/RoastDuck-CFWPack) —— 为中国宝宝定制
  - ~~[carcaschoi/ShallowSea](https://github.com/xingshijie/ShallowSea) —— Repository unavailable due to DMCA takedown.~~

- 系统固件

  ```bash
  # 系统版本： 新游戏一般需要同时期新系统
  # 真实系统可在线升级/离线升级 【大版本只能升不能降！！！】
  # 虚拟系统只能离线升级 【随便玩，一般只在虚拟系统升级系统】
  # 固件：
  12.1.0 #
  15.0.1 #
  18 # 24
  19 # 24
  ```

  - [THZoria/NX_Firmware](https://github.com/THZoria/NX_Firmware) —— 离线固件下载

- 大佬
  - [carcaschoi](https://www.youtube.com/@carcaschoi)
  - [illyasever](https://space.bilibili.com/36010)
  - 参考：
    - <https://www.marsshen.com/posts/20e16ead/>
    - <RepoLink path="/archive/RDPv8.8.1烤鸭包使用教程.pptx" />

```bash
# 整合包目录
emuMMC —— 虚拟系统（Custom FirmWare，CFW）。 （sysMMC = 真实系统）
Nintendo —— 正版系统文件（运行时生成的上下文，最好保留）
switch —— 存放自制插件
...
```

```bash
# 常用工具
Daybreak （破晓） —— 升级固件
All-in-One Switch Updater
EdiZon —— 作弊工具
sys-clk manager —— 超频
DBI —— 安装/文件管理 （不开源）  https://github.com/rashevskyv/dbi
Awoo Installer —— 安装 https://github.com/Huntereb/Awoo-Installer
DeepSea Toolbox —— 后台管理
Tesla
```

安装包

- nsp —— nsp=数字版（需要安装，安装完后安装包+游戏本体要两倍空间）
- xci —— 卡带版（无需安装，换游戏需要切换卡带）（实际安装一样需要两倍空间）
- nsz
- xcz

游戏

- 塞尔达系列
  - 塞尔达传说：智慧的再现 （The Legend of Zelda Echoes of Wisdom）
