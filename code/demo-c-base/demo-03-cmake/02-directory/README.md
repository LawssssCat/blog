# 分层目录Demo

```bash
build
Shape       # lib库1
  - src
  - include
  - CMakeLists.txt
myLib       # lib库2
test        # 测试用例
main.cpp    # 可执行程序
config.h.in # 版本模板文件，提供MYPROJECT_VERSION_MINOR/MYPROJECT_VERSION_MAJOR宏定义
CMakeLists.txt
```

下载依赖

```bash
yum install openssl-devel
```

修改调试参数： `.vscode/settings.json#cmake.debugConfig.args`
