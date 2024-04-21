---
title: Maven 配置
date: 2024-04-13
tag:
  - maven
order: 10
---

介绍 maven settings.xml 和 pom.xml 配置文件的使用。

<!-- more -->

Maven 配置有两种：

- **settings.xml**

  settings.xml 是用来设置 maven 参数的配置文件。
  settings.xml 中包含类似本地仓储位置、修改远程仓储服务器、认证信息等配置。

- **pom.xml**

  settings.xml 是 maven 的全局配置文件，而 pom.xml 文件是所在项目的局部配置。

## 配置文件位置

pom.xml 文件一般位于项目根目录。

settings.xml 文件一般存在于两个位置：

- 全局配置: `${M2_HOME}/conf/settings.xml`
- 用户配置： `~/.m2/settings.xml`

## 配置优先级

核心： **局部配置优先于全局配置**。

配置优先级从高到低： pom.xml > user settings > global settings

::: info
如果这些文件同时存在，在应用配置时，会合并它们的内容，如果有重复的配置，优先级高的配置会覆盖优先级低的。
:::

```bash
mvn -X # 查看 settings.xml 文件的读取顺序
mvn help:effective-settings # 查看当前生效的 settings.xml
```

## pom.xml 元素详解

### 资源（resource）

```xml
<!-- 构建项目需要的信息 -->
<build>
    <!-- 该元素设置了项目源码目录，当构建项目的时候，构建系统会编译目录里的源码。该路径是相对
         于pom.xml的相对路径。 -->
    <sourceDirectory></sourceDirectory>
    <!-- 该元素设置了项目单元测试使用的源码目录，当测试项目的时候，构建系统会编译目录里的源码。
         该路径是相对于pom.xml的相对路径。 -->
    <testSourceDirectory></testSourceDirectory>
    <!-- 被编译过的应用程序class文件存放的目录。 -->
    <outputDirectory></outputDirectory>
    <!-- 被编译过的测试class文件存放的目录。 -->
    <testOutputDirectory></testOutputDirectory>
    <!-- 这个元素描述了项目相关的所有资源路径列表，例如和项目相关的属性文件，这些资源被包含在
         最终的打包文件里。 -->
    <resources>
        <!-- 这个元素描述了项目相关或测试相关的所有资源路径 -->
        <resource>
            <!-- 描述了资源的目标路径。该路径相对target/classes目录（例如${project.build.outputDirectory}）。
                 举个例子，如果你想资源在特定的包里(org.apache.maven.messages)，你就必须该元素设置为
                org/apache/maven/messages。然而，如果你只是想把资源放到源码目录结构里，就不需要该配置。 -->
            <targetPath></targetPath>
            <!-- 是否使用参数值代替参数名。参数值取自properties元素或者文件里配置的属性，文件在filters元素
                 里列出。 -->
            <filtering>false</filtering>
            <!-- 描述存放资源的目录，该路径相对POM路径 -->
            <directory>src/main/java</directory>
            <!-- 包含的模式列表，例如**/*.xml. -->
            <includes>
                <include>**/*.properties</include>
	              <include>**/*.xml</include>
	              <include>**/*.yaml</include>
            </includes>
            <!-- 排除的模式列表，例如**/*.xml -->
            <excludes>
                <exclude></exclude>
            </excludes>
        </resource>
    </resources>
</build>
```

### 插件（plugin）

```xml
<!-- 构建项目需要的信息 -->
<build>
    <!-- 子项目可以引用的默认插件信息。该插件配置项直到被引用时才会被解析或绑定到生命周期。给定插件的任何本
         地配置都会覆盖这里的配置 -->
    <pluginManagement>
        <!-- 使用的插件列表 。 -->
        <plugins>
            <!-- plugin元素包含描述插件所需要的信息。 -->
            <plugin>
                <!-- 插件在仓库里的group ID -->
                <groupId></groupId>
                <!-- 插件在仓库里的artifact ID -->
                <artifactId></artifactId>
                <!-- 被使用的插件的版本（或版本范围） -->
                <version></version>
                <!-- 在构建生命周期中执行一组目标的配置。每个目标可能有不同的配置。 -->
                <executions>
                    <!-- execution元素包含了插件执行需要的信息 -->
                    <execution>
                        <!-- 执行目标的标识符，用于标识构建过程中的目标，或者匹配继承过程中需要合并的执行目标 -->
                        <id></id>
                        <!-- 绑定了目标的构建生命周期阶段，如果省略，目标会被绑定到源数据里配置的默认阶段 -->
                        <phase></phase>
                        <!-- 配置的执行目标 -->
                        <goals></goals>
                        <!-- 作为DOM对象的配置 -->
                        <configuration></configuration>
                    </execution>
                </executions>
                <!-- 作为DOM对象的配置 -->
                <configuration></configuration>
            </plugin>
        </plugins>
    </pluginManagement>

    <!-- 该项目使用的插件列表 。 -->
    <plugins>
        <!-- plugin元素包含描述插件所需要的信息。 -->
        <plugin>
            <!-- 插件在仓库里的group ID -->
            <groupId></groupId>
            <!-- 插件在仓库里的artifact ID -->
            <artifactId></artifactId>
            <!-- 被使用的插件的版本（或版本范围） -->
            <version></version>
            <!-- 作为DOM对象的配置 -->
            <configuration></configuration>
        </plugin>
    </plugins>
</build>
```

---

参考：

- todo https://www.cnblogs.com/jingmoxukong/p/6050172.html?utm_source=gold_browser_extension
- todo https://blog.csdn.net/weixin_42201180/article/details/127121085
