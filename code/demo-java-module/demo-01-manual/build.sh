#!/bin/bash

set -x

java -version

# 编译class
# javac -d bin src/hello.world/module-info.java src/hello.world/org/example/*.java
javac -d bin --module-source-path src/ --module hello.world
(cd bin/hello.world; java org.example.Main)

# 打包jar
jar --create --file hello.jar --main-class org.example.Main -C bin/hello.world .
jar -t -f hello.jar # 查看内部文件目录
jdeps -s hello.jar # 查看内部模块、模块依赖关系
java -jar hello.jar
java --module-path hello.jar --module hello.world # ok 指定文件
java --module-path . --module hello.world # ok 指定目录

# 打包module
rm hello.jmod
jmod create --class-path hello.jar hello.jmod
java --module-path hello.jmod --module hello.world # todo
