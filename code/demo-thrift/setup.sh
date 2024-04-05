#!/bin/bash
#
# thrift 安装脚本
#
# 参考：
# + 安装脚本 —— https://thrift.apache.org/docs/install/centos
# + 环境要求 —— https://thrift.apache.org/docs/install/
#

WORKSPACE=$1
if [ -n "$WORKSPACE" ] || WORKSPACE=$(mktemp -d)
cd $WORKSPACE

THRIFT_VERSION="0.20.0"
THRIFT_HOME="${WORKSPACE}/thrift-install/${THRIFT_VERSION}/"

function install_src_thrift() {
  cd ${WORKSPACE}
  if [ ! -d "thrift-${THRIFT_VERSION}" ]; then
    wget https://dlcdn.apache.org/thrift/${THRIFT_VERSION}/thrift-${THRIFT_VERSION}.tar.gz
    tar -zxvf thrift-${THRIFT_VERSION}.tar.gz
  fi
}

function install_yum() {
  yum -y install wget
  yum -y groupinstall "Development Tools"
  yum -y install libtool flex bison pkgconfig boost-devel libevent-devel zlib-devel python-devel ruby-devel openssl-devel
  yum -y install ant
  yum -y install boost
}

function build() {
  cd thrift-${THRIFT_VERSION}

  local CONFIGURE_FLAGS=""
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --prefix=$THRIFT_HOME"
  # CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --with-cpp --with-boost=/usr/local/" # 源码编译 boot 则指定 boost 位置。否则 “g++: error: /usr/local//lib/libboost_unit_test_framework.a: No such file or directory”
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --with-cpp" # 使用 boost 默认位置
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --with-python"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --with-java" # ant
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-csharp"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-erlang"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-perl"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-php --without-php_extension"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-ruby"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-haskell"
  CONFIGURE_FLAGS="${CONFIGURE_FLAGS} --without-go"

  ./bootstrap.sh
  ./configure ${CONFIGURE_FLAGS}

: <<COMMENT
thrift 0.20.0

Building C (GLib) Library .... : no
Building C++ Library ......... : yes
Building Common Lisp Library.. : no
Building D Library ........... : no
Building Dart Library ........ : no
Building .NET Standard Library : no
Building Erlang Library ...... : no
Building Go Library .......... : no
Building Haxe Library ........ : no
Building Java Library ........ : no # ???? 确定有 java 和 ant 的。而且编译后又确实能生成 java 代码？
Building Kotlin Library ...... : no
Building Lua Library ......... : no
Building NodeJS Library ...... : no
Building Perl Library ........ : no
Building PHP Library ......... : no
Building Python Library ...... : yes
Building Py3 Library ......... : yes
Building Ruby Library ........ : no
Building Rust Library ........ : no
Building Swift Library ....... : no

C++ Library:
   C++ compiler .............. : g++ -std=c++11
   Build TZlibTransport ...... : yes
   Build TNonblockingServer .. : yes
   Build TQTcpServer (Qt5) ... : no
   C++ compiler version ...... : g++ (GCC) 10.3.1

Python Library:
   Using Python .............. : /usr/bin/python3
   Using Python version ...... : Python 3.9.9
   Using Python3 ............. : /usr/bin/python3
   Using Python3 version ..... : Python 3.9.9

If something is missing that you think should be present,
please skim the output of configure to find the missing
component.  Details are present in config.log.
COMMENT

  make
  make install
}
