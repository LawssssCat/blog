#!/bin/bash

# 说明：需要手动指定所有模块位置，否则报错
# e.g.
# $ java --module-path "./out/production/demo-02-IntelliJ" --module "demo.main/org.example.Main"
# Error occurred during initialization of boot layer
# java.lang.module.FindException: Module demo.lib not found, required by demo.main
java --module-path "./out/production/demo-02-IntelliJ;./out/production/MyLib" --module "demo.main/org.example.Main"

java -jar "./out/artifacts/demo_02_IntelliJ_jar/demo-02-IntelliJ.jar"
# --add-opens demo.lib/org.example.lib=demo.main
