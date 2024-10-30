---
title: 编译 Java 代码为可执行文件
date: 2024-10-28
tag:
  - java
order: 99
---

参考：

- <https://www.cnblogs.com/xiaomandujia/p/17818156.html>
- <https://stackoverflow.com/questions/147181/how-can-i-convert-my-java-program-to-an-exe-file>
- archive [Convert Java to EXE — Why, When, When Not and How](https://web.archive.org/web/20181117071040/https://www.excelsior-usa.com/articles/java-to-exe.html)
- archive [Best JAR to EXE Conversion Tools, Free and Commercial](https://web.archive.org/web/20160808182011/https://www.excelsior-usa.com/articles/best-jar-to-exe-conversion-tools-free-commercial.html)

| 软件                       | 协议        | 官网                                                        | 代码仓                                                                                    | 创建时间 | 使用量 | 原理           |
| -------------------------- | ----------- | ----------------------------------------------------------- | ----------------------------------------------------------------------------------------- | -------- | ------ | -------------- |
| JavaPackager               |             | <https://docs.oracle.com/javase/10/tools/javapackager.htm>  |
| GraalVM                    |
| packr                      | Apache2     | <https://github.com/libgdx/packr>                           |
| WinRun4J                   |             | <https://winrun4j.sourceforge.net/>                         | <https://github.com/poidasmith/winrun4j>                                                  |
| launch4j                   | BSD3,MIT    | <https://launch4j.sourceforge.net/>                         | <git://git.code.sf.net/p/launch4j/git> <br> （镜像） <https://github.com/mirror/launch4j> |          |        | 包装 Jar       |
| JSmooth                    | GPL,LGPL    | <https://jsmooth.sourceforge.net/>                          |                                                                                           |          |        | 包装 Jar       |
| JexePack                   | 商业        | <https://www.duckware.com/jexepack/>                        |                                                                                           |          |        | 包装 Jar       |
| exe4j                      | 商业        | <https://www.ej-technologies.com/exe4j/>                    |
| installAnywhere            | 商业        | <https://www.revenera.com/install/products/installanywhere> |
| advancedInstaller          | 商业        | <https://www.advancedinstaller.com/java.html>               |                                                                                           |          |        | 生成 msi       |
| jar2exe                    | 商业        | <https://jar2exe.com/>                                      |
| ~~gcj~~（2017 年停止维护） |             | <https://gcc.gnu.org/java/>                                 |                                                                                           |          |        | AOT            |
| Excelsior JET              |             | ~~<www.excelsiorjet.com>~~                                  | <https://github.com/excelsior-oss/excelsior-jet-maven-plugin>                             |          |        | AOT            |
| RoboVM                     | GPL,Apache2 | <https://mobivm.github.io/>                                 | <https://github.com/MobiVM/robovm>                                                        |          |        | AOT (IOS only) |
| Atego PERC                 | 商业        | ~~<http://atego.com/products/atego-perc>~~                  |                                                                                           |          |        | AOT            |
