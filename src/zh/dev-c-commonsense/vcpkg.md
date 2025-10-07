---
title: vcpkg 使用笔记
order: 10
---

[vcpkg](https://vcpkg.io/en/)是Microsoft开发并开源的C++包管理工具，旨在简化C++库的获取、构建和集成过程。
vcpkg可以在Windows、Macos、Linux平台上运行，可以在编译跨平台的C/C++项目时方便的对第三方依赖库进行管理。

工作流程：

1. 下载依赖
1. 定位cmake项目到vcpkg
1. 通过`find_package`使用下载好的依赖

工作原理：

+ vcpkg本质是命令行工具，结合Git仓库管理其“端口文件”。
+ 每个“端口文件”是一个文件夹，里面描述了库如何下载、配置、编译脚本（portfile.cmake）和元数据（CONTROL文件）
+ 用户通过命令（`vcpkg install zlib`）触发vcpkg下载源码、应用补丁、配置和编译库，最终生成可用的静态库或动态库。

<!-- more -->

特点：

+ 跨平台支持 —— Windows/Linux/MacOS
+ 集成简单 —— 支持VisualStuaio、CMake无缝集成
+ 支持主流库 —— 支持上千个常用C/C++库，如OpenCV、Boost、SDL、fmt、OpenSSL等
+ 支持版本控制（Manifest模式） —— 可以在项目中精确指定依赖库的版本，保证构建一致。
+ 自定义移植（Port）机制 —— 允许用户自定义或维护自己需要的库

## 安装

::: tabs

@tab vs/clion

```bash
git clone git@github.com:microsoft/vcpkg.git
cd vcpkg
# ./bootstrap-vcpkg.bat
./bootstrap-vcpkg.sh # 环境变量 PATH=$(pwd):$PATH
vcpkg --version
```

@tab vscode

```json title=".vscode/settings.json"
{
  "cmake.configureSettings": {
    "CMAKE_TOOLCHAIN_FILE": "${VCPKG_ROOT}\\scripts\\buildsystems\\vcpkg.cmake"
  }
}
```

:::

## 用户模式

```bash
vcpkg integrate install
```

```bash
vcpkg list

vcpkg install zlib # 下载到 "安装/installed" 目录
# set http_proxy=http://127.0.0.1:10808
# set https_proxy=http://127.0.0.1:10808
# git config --global http.proxy 'socks5://127.0.0.1:10808'
# git config --global https.proxy 'socks5://127.0.0.1:10808'
```

::: tip

下载目标的命名规则： `<name>[:<arch>-[mingw]-[dynamic(default)|static]-<platform>]`

如果是windows+mingw的话，一般需要 `x64-mingw-dynamic` 版本。

:::

## 清单模式

```bash
vckpg new --application
```

```bash
# 搜索
vcpkg search zlib
# 添加
vcpkg add port zlib
```

```bash
vcpkg install
```

```json title="vcpkg.json"
{
  "name": "demo",
  "version-string": "8.2.0",
  "port-version": 2,
  "description": "A description of the library.",
  "dependencies": [
    {
      "name": "curl",
      "default-features": false,
      "features": [
        "winssl"
      ],
      "platform": "windows"
    },
    "rapidjson"
  ]
}
```
