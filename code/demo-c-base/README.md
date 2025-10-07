# C/C++开发环境集合

用vagrant管理虚拟机Fedora系统。
方便在Linux中搭建C/C++的开发环境。

目录：

+ 各级别开发环境
  + [vs:gcc,gdb](demo-01-workspace/01-gcc-gdb)
  + [vs:gcc,cmake,gdb](demo-01-workspace/02-gcc-cmake-gdb)
  + [vs:gcc,cmake,gtest,gdb](demo-01-workspace/03-gcc-cmake-gtest-gdb)
  + [vs:vcpkg,gcc,cmake,gtest,gdb](demo-01-workspace/04-vcpkg-gcc-cmake-gtest-gdb)
  + [vs:vcpkg,clang-llvm,cmake,gtest,lldb](demo-01-workspace/01-vcpkg-clang_llvm-cmake-gtest-gdb) —— best
+ ~~makefile使用例子 [link_makefile_demo](./demo-02-makefile/)~~ （请忽略）
+ cmake使用例子 [link_cmake_demo](./demo-03-cmake/)
+ c++语法测试用例 [link_syntax_cpp](./demo-05-syntax/)

## 常用Vagrant操作

1、虚拟机管理

```bash
# 启动虚拟机
$ vagrant up

# 重载虚拟机设置
$ vagrant reload             # 重载： Vagrantfile
$ vagrant provision          # 重载： vagrant.sh
$ vagrant reload --provision # 重载： Vagrantfile + vagrant.sh

# 销毁虚拟机
$ vagrant destroy
```

2、会话登录

```bash
# 方式1： 使用vagrant功能
# 问题： 没法方便的在xshell、vscode等会话管理软件上
$ vagrant ssh

# 方式2： 使用公开密钥对 （❗勿在公网使用）
$ # cat .ssh/id_ed25519
$ # cat .ssh/id_ed25519.pub
# e.g.
# $ vim ~/.ssh/config
# Host vagrant-cppdev
#   HostName 192.168.56.20
#   User vagrant
#   IdentityFile .ssh/id_ed25519
```
