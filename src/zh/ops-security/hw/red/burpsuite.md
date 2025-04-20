---
title: BurpSuite 介绍
---

> dalao blog's：
>
> - https://mrxn.net/
>
> 入门：
>
> - https://www.bilibili.com/video/BV1hN411J7W2
> - ~~todo https://www.bilibili.com/video/BV1Ja4y1Z7ga/~~

## 安装

github burpsuite loader

https://github.com/h3110w0r1d-y/BurpLoaderKeygen/?tab=readme-ov-file
https://github.com/TrojanAZhen/BurpSuitePro-2.1?tab=readme-ov-file

```bash
https://portswigger.net/burp/releases/download?product=pro&version=2024.6.5&type=jar
https://portswigger.net/burp/releases/professional-community-2024-6-5

burp-keygen-scz
SHA256: 74893842a782238f52f0f225c06fa744568321911fea077bc290bd9914b73402
MD5: e646b3eef03efba637e6ed794ced4114

BurpLoaderKeygen_v1.17
SHA1: 3D0E27962C83C8DB2955FA79C27256442C0BCB55
MD5: A3B1B3BE59E6D3D374A2D3344213E6C7
```

```bash
$ md5sum *
a3b1b3be59e6d3d374a2d3344213e6c7  BurpLoaderKeygen_v1.17.jar
3a897404dd5a104d70830ff4d59d8aa6  BurpSuite.ico
c96cab332d6d98a6dd82d5ca8c5ab6dc  README.md
e646b3eef03efba637e6ed794ced4114  burp-keygen-scz.jar
$ 快捷方式
C:\my\java\jdk22\openjdk-22_windows-x64_bin\jdk-22\bin\java.exe -jar .\BurpLoaderKeygen_v1.17.jar
$ .config.ini
auto_run=1
early=0
ignore=1
$ md5sum *
26865194347d5600e3cdc36d76dce28a  .config.ini
a3b1b3be59e6d3d374a2d3344213e6c7  BurpLoaderKeygen_v1.17.jar
e646b3eef03efba637e6ed794ced4114  burp-keygen-scz.jar
6778d4b8d113134de9db1e6019d2159e  burpsuite_pro_v2024.6.5.jar
b2400441eb8447c198b36b60196a1a1f  burpsuite_pro_v2024.6.5.lnk
```

## 基本使用

拦截/过滤

1. proxy
1. proxy/history
1. proxy/intercept/on
1. target/Add to Scope
1. proxy/history/filter only scope

重放

1. proxy/history/send to repeater
1. repeater/`${choice}`/send

扫描

1. Dashboard/new scan
   1. options/webapp scan
   1. scan details
      1. scan type/crawl（爬虫） and audit（审计）
      1. Urls to scan
   1. scan configuration/use a preset scan mode/lightweight
   1. application login/login type?
   1. resource pool/e.g. concurrent request/...
1. Target/Site map/Issue/Report issue for this host （导出报告）

## 代理模块

开启代理监听：
Proxy/Proxy settings/Proxy listeners
（手动安装证书）

浏览器代理插件：
Proxy SwitchyOmega

## 爆破模块

爆破：对指定接口进行不同的填充，测试不同输入返回的结果。

1. proxy/history/send to Intruder（入侵/打扰）
1. intruder/positions/Choose an attack type/sniper
   - sniper（狙击手） —— 一组 playload，逐个填充爆破点
   - battering ram（重锤/工程锤） —— 一组 playload，统一填充爆破点
   - pitchfork（杈子） —— 多组 playload，各自填充爆破点，非笛卡尔积，完成一组爆破就结束
   - cluster bomb（集束炸弹） —— 多组 playload，各自填充爆破点，笛卡尔积
1. intruder/Settings/Grep - Math/Extract （包含/排除）

## 扩展

todo

todo 外带 dns/email/...

todo demo 点击挟持/...

## 上下游代理

sqlmap -> burpsuite -> [xray](https://github.com/chaitin/xray) -> ...
