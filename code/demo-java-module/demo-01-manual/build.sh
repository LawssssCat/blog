#!/bin/bash

set -x

java -version

# 编译class
javac -d bin src/module-info.java src/org/example/*.java
(cd bin; java org.example.Main)

# 打包jar
jar --create --file hello.jar --main-class org.example.Main -C bin .
java -jar hello.jar

# 打包module
rm hello.jmod
jmod create --class-path hello.jar hello.jmod
java --module-path hello.jmod --module hello.world
