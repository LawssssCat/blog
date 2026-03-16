#!/bin/bash

set -ex

java -version

# 编译class
rm bin -rf
# javac -d bin --module-source-path src/ --module demo.svc,demo.svc.impl,demo.main
# javac -d bin --module-source-path src/ --module demo.main # ❗
javac -d bin --module-source-path src/ --module demo.svc.impl,demo.main
# java --class-path bin/demo.main/:bin/demo.svc/:bin/demo.svc.impl/ org.example.main.Main # ❗todo

# 打包jar
rm jars -rf
jar --create --file jars/demo.jar --main-class org.example.main.Main -C bin/demo.main .
jar --create --file jars/svc.jar -C bin/demo.svc .
jar --create --file jars/svc-impl.jar -C bin/demo.svc.impl .
jar -t -f jars/demo.jar
jar -t -f jars/svc.jar
jar -t -f jars/svc-impl.jar
jdeps --module-path jars -s jars/demo.jar
jdeps --module-path jars -s jars/svc.jar
jdeps --module-path jars -s jars/svc-impl.jar
java --module-path jars --module demo.main

# 打包module
rm demo.jmod -f
jmod create --class-path jars/demo.jar demo.jmod
java --module-path demo.jmod --module demo.main # todo
