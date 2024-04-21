---
title: Maven 插件列表
date: 2024-04-20
tag:
  - maven
order: 93
---

整理 maven 常用/常见的插件。

<!-- more -->

todo

- jacoco-maven-plugin
- build-helper-maven-plugin

## 开源协议 license

todo `license-maven-plugin` —— 扫描项目中依赖的库的 LICENSE，对一些商业不友好的 LICENSE 发出失败告警。

## 依赖分析 dependency

[`maven-dependency-plugin`](https://maven.apache.org/plugins/maven-dependency-plugin/)

> 参考：
>
> - <https://blog.csdn.net/txhlxy/article/details/136269831>
> - <https://www.cnblogs.com/lianshan/p/7350614.html>

依赖分析

- list
- tree
- list-repositories
- analyze —— 分析未使用的直接和间接依赖、分析依赖的使用范围是否定义正确

  ```bash
  $ mvn dependency:analyze
  ...
  [WARNING]

  Some dependencies of Maven Plugins are expected to be in provided scope.
  Please make sure that dependencies listed below declared in POM
  have set '<scope>provided</scope>' as well.

  The following dependencies are in wrong scope:
  * org.apache.maven:maven-model:jar:3.6.3:compile
  ```

打包依赖

- copy —— 直接复制 jar 包到指定位置
- unpack —— 解压 jar 包到指定位置

## 代码格式 formatter

todo

## 代码风格 checkstyle

todo `maven-checksytle-plugin`

## 静态检查

静态检查希望对代码库或二进制文件在不运行任何相关代码的情况下进行测试，以发现潜在的问题、确保代码质量。

- CheckStyle —— 代码规范检查工具
- PMD —— 代码编码缺陷检查工具，如：未使用的变量、空 try\-catch 块、不必要的对象创建
- Findbugs —— 将编译后的字节码与一组错误模型对比，识别出代码中的缺陷。如：未使用的错误方法、空指针引用、资源泄露等

todo `sonar-maven-plugin` 进行 sonarqube 扫描

todo `org.owasp/dependency-check-maven` 使用 dependency check core 扫描公开披露的与项目有依赖关系的漏洞。生成报告，列出依赖项、任何已识别的 CPE（公共平台枚举） 表示以及 CVE（公共漏洞和暴露） 条目。

## 环境归一 enforcer

[`maven-enforcer-plugin`](https://maven.apache.org/enforcer/maven-enforcer-plugin/) —— 默认会在 validate 后执行 `enforcer:enforce`，然后对项目环境进行检查。

> enforce 执行

```xml
<properties>
  <java.version>1.7</java.version>
  <maven.version>3.3.3</maven.version>
</properties>

... plugins ...

<plugin>
  <artifactId>maven-enforcer-plugin</artifactId>
  <version>1.4.1</version>
  <executions>
    <!-- 规定编译环境 -->
    <execution>
      <id>default-cli</id>
        <goals>
          <goal>display-info</goal>
          <goal>enforce</goal>
        </goals>
        <phase>validate</phase>
        <configuration>
          <rules>
            <requireJavaVersion> <!-- 规定 JDK 版本 -->
              <message> <!-- 失败后提示消息 -->
                <![CDATA[You are running an older version of Java. This application requires at least JDK ${java.version}.]]>
              </message>
              <version>[${java.version}.0,)</version> <!-- 规定 JDK 版本规则 -->
            </requireJavaVersion>
            <requireMavenVersion> <!-- 规定 maven 版本 -->
              <message>
                  <![CDATA[You are running an older version of Maven. This application requires at least Maven ${maven.version}.]]>
              </message>
              <version>[${maven.version},)</version>
            </requireMavenVersion>
            <bannedDependencies> <!-- 检查依赖 -->
              <message>don't use TestNG, must use JUnit</message>
              <excludes>
                <!--groupId[:artifactId][:version][:type][:scope][:classifier]-->
                <exclude>org.testng:testng</exclude>
              </excludes>
              <searchTransitive>true</searchTransitive> <!-- 是否检查传递性依赖（间接依赖） -->
            </bannedDependencies>
            <requireOS>
                <name>mac os x</name>
                <family>mac</family>
                <arch>x86_64</arch>
                <version>10.10.3</version>
            </requireOS>
            <requireProperty>
              <message>"Project version must be specified."</message>
              <property>project.version</property>
              <regex>.*(\d|-SNAPSHOT)$</regex>
              <regexMessage>"Project version must end in a number or -SNAPSHOT."</regexMessage>
            </requireProperty>
          </rules>
        </configuration>
      </execution>
      <!-- 规定发布环境 -->
      <execution>
        <id>enforce-install</id>
        <goals>
            <goal>enforce</goal>
        </goals>
        <phase>install</phase>
        <configuration>
          <rules>
            <requireProperty>
              <property>project.version</property>
              <message>"Project version must be specified."</message>
              <regex>.*(\d)$</regex>
              <regexMessage>"Project version must end in a number."</regexMessage>
            </requireProperty>
          </rules>
        </configuration>
    </execution>
    </executions>
</plugin>
```

## 资源配置 resources

`maven-resources-plugin` —— 将 `src/main/resources` 目录中的资源文件单独剥离出来，生成一个独立的资源文件包。

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-resources-plugin</artifactId>
  <version>3.2.0</version>
  <executions>
    <execution>
      <id>copy-resources</id>
      <phase>package</phase> <!-- 注意资源复制阶段 -->
      <goals>
        <goal>copy-resources</goal>
      </goals>
      <configuration>
        <outputDirectory>${project.build.directory}/resources</outputDirectory>
        <resources>
          <resource>
            <directory>src/main/resources</directory>
            <includes>
              <include>**/*</include>
            </includes>
          </resource>
        </resources>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## 编译选项 compiler

todo `maven-compiler-plugin`

## 开发测试 surefire

todo `maven-surefire-plugin` 统计编译时间、测试时间

## Ant 脚本 antrun

todo `maven-antrun-plugin` https://www.bilibili.com/video/BV1au4y1e7rE?p=14

## 代码混淆 proguard

todo `proguard-maven-plugin` https://www.bilibili.com/video/BV1au4y1e7rE?p=7

## 源码打包 source

`maven-source-plugin` —— 构建源码包 xxx-sources.jar

```xml
<plugin>
  <artifactId>maven-source-plugin</artifactId>
  <version>3.2.1</version>
  <configuration>
    <!-- 源码包随 xx.jar 安装到本地仓库/私服/远程仓库 -->
    <attach>true</attach>
  </configuration>
  <executions>
    <execution>
      <phase>compile</phase>
      <goals>
        <goal>jar</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

## 全量打包 assembly

参考：

- https://www.cnblogs.com/lianshan/p/7348093.html

`maven-assembly-plugin` —— 将项目中的依赖项、模块、文件和其他任意文件一起组装成一个可分发的归档文件 zip/gz/jar 等。

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-assembly-plugin</artifactId>
  <version>3.3.0</version>
  <configuration>
    <archive>
      <!-- 为 MANIFEST.MF 文件添加 Main-Class -->
      <!-- 相当于：
        <manifestEntries>
          <Main-Class>org.example.AssemblyTestApp</Main-Class>
        </manifestEntries>
      -->
      <manifest>
        <mainClass>org.example.AssemblyTestApp</mainClass>
      </manifest>
      <!--
        <manifestEntries>
          <premain-Class>org.example.XxxAgent</premain-Class>
          <Can-Redefine-Classes>true</Can-Redefine-Classes>
          <Can-Retransform-Classes>true</Can-Retransform-Classes>
        </manifestEntries>
      -->
    </archive>
    <!-- 配置描述符文件 assembly.xml： （💡将配置写到单独文件中，方便管理和复用）
      <descriptors>
        <descriptor>${basedir}/src/main/assembly/assembly.xml</descriptor>
      </descriptors>
    -->
    <descriptorRefs>
      <!-- 💡这里 jar-with-dependencies 表示打包编译结果时带上所有的依赖 jar 包，这些 jar 会被解压后再平铺到最终生成的 jar 中。 -->
      <descriptorRef>jar-with-dependencies</descriptorRef>
    </descriptorRefs>
    <!-- 默认打出来的 jar 包名称带有 jar-with-dependencies 后缀。 false 表示去掉该后缀 -->
    <appendAssemblyId>false</appendAssemblyId>
    <!-- 配置 assembly 插件最终输出的文件名称 -->
    <finalName>${project.build.finalName}-single</finalName>
  </configuration>
  <executions>
    <execution>
      <id>one_jar</id>
      <phase>package</phase>
      <goals>
        <goal>single</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

> **什么是 fat-jar？**
>
> `fat-jar` 又叫 `uber-jar`, `jar-with-dependencies`，指包含了运行时所需的依赖的 jar 包，即一个 "all in one" 的 jar 包。这样我们可以拿到 `fat-jar` 后就可以直接运行 jar，而无需再寻找环境依赖。
>
> 制作 `fat-jar` 的方法是使用 maven 插件打包：
>
> - springboot 应用
>   - 通过 `spring-boot-maven-plugin` 插件实现了 `fat-jar` 打包。（嵌套 jar "jar of jars" 打包形式 —— 目录结构：classes + lib/依赖.jar(s)）
> - 非 springboot 应用
>   - 可选用 `maven-assembly-plugin` 插件： 解压所有 jar 文件，再与目标程序重新打包成一个 "all in one" 的 jar。
>   - 可选用 `maven-shade-plugin` 插件： （遮蔽）功能类似 assembly，但支持依赖包的重命名（避免版本冲突）、配置文件的附加等。

## 全量打包 shade

`maven-shade-plugin` —— 功能类似 assembly，但支持依赖包的重命名（避免版本冲突）、配置文件的追加（避免覆盖）等。

```xml
<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-shade-plugin</artifactId>
  <version>3.2.4</version>
  <executions>
    <execution>
      <phase>package</phase>
      <goals>
        <goal>shade</goal>
      </goals>
      <configuration>
        <!--
          原始 jar 名称： xx.jar
          输出 jar 名称：
          true —— xx-shaded.jar
          false —— xx.jar 并修改原始 jar 名称为 original-xx.jar
        -->
        <shadedArtifactAttached>false</shadedArtifactAttached>
        <!-- 生成名为 dependency-reduced-pom.xml。这个 xml 去掉了原始 pom 中已经被包含进 fat-jar 的依赖项 -->
        <createDependencyReducedPom>true</createDependencyReducedPom>
        <!-- 类重定位 -->
        <relocations>
          <relocation>
            <pattern>org.apache.commons.lang3</pattern>
            <shadePattern>org.apache.commons.lang3.shaded</shadePattern>
          </relocation>
        </relocations>

        <transformers>
          <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
            <!-- 为 MANIFEST.MF 文件添加指定的 key/alue -->
            <manifestEntries>
              <Main-Class>org.example.ShadeTestApp</Main-Class>
              <Build-Number>123</Build-Number> <!-- 自定义的参数和参数值 -->
            </manifestEntries>
          </transformer>
        </transformers>

        <!-- 指定要排除/包含的依赖 groupId:artifactId[[:type]:classifier] （支持 * 和 ? 通配符） -->
        <artifactSet>
          <excludes>
            <exclude>*:hutool-all</exclude>
          </excludes>
        </artifactSet>
        <!-- 更细粒度的控制排除/包含的依赖 -->
        <filters>
          <filter>
            <artifact>*.*</artifact>
            <excludes>
              <exclude>META-INF/LICENSE*</exclude>
              <exclude>META-INF/NOTICE*</exclude>
              <exclude>META-INF/MANIFEST*</exclude>
            </excludes>
          </filter>
        </filters>
      </configuration>
    </execution>
  </executions>
</plugin>
```

## 全量打包 spring-boot

`spring-boot-maven-plugin` —— 打包 springboot 项目 todo https://www.bilibili.com/video/BV1au4y1e7rE?p=11

## 归档制品 mdeploy

`aven-deploy-plugin` —— Maven 自带 deploy 插件。

该插件主要有两个目标 deploy 和 deploy-file：

- `deploy:deploy` 用于自动安装工件、其 POM 和特定项目生成的附加工件。与部署相关的大多数（如果不是全部）信息都存储在项目的 pom 中。
- `deploy:deploy-file` 用于安装单个工件及其 pom。 （需要指定相对多的参数）

### 目标 deploy

:::: tabs

@tab 仓库位置

```xml title="settings.xml"
<mirror>
  <id>nexus-mine</id>
  <mirrorOf>central</mirrorOf>
  <name>Nexus mine</name>
  <url>http://localhost:8081/repository/maven-public/</url>
</mirror>
```

@tab 认证信息

```xml title="settings.xml"
<server>
  <id>nexus-mine</id>
  <username>admin</username>
  <password>xxx</password>
</server>
```

::: warning
这里配置需要设置明文密码，这一般是不被允许的。

加密方案： todo 对比

- 配置环境变量
- maven 配置加密。参考： <https://maven.apache.org/guides/mini/guide-encryption.html>

:::

@tab 部署位置

```xml title="pom.xml"
<distributionManagement>
  <snapshotRepository>
    <!-- id 需要与 settings.xml 上一致 -->
    <id>nexus-mine</id>
    <url>http://localhost:8081/repository/maven-snapshots/</url>
  </snapshotRepository>
</distributionManagement>
```

@tab 设置坐标

```xml title="pom.xml"
<!-- 上传的坐标 -->
<groupId>...</groupId>
<!-- 上传的包名 -->
<artifactId>...</artifactId>
<!-- 上传的版本 -->
<version>...</version>
```

::: tip
Maven 制品是按照 groupId/artifactId/version 路径存储的
:::
::::

上传命令

```bash
mvn clean deploy -s ./settings.xml -gs ./settings.xml

# -s,--settings <arg> —— Alternate path for the user settings file
# -gs,--global-settings <arg> —— Alternate path for the global settings file
```

### 目标 deploy-file

```bash
mvn deploy:deploy-file \
-Dfile=D:\xxx\com.xxx.test-1.0.0.jar \
-DpomFile=./pom.xml \
-DgroupId=com.example.xxx \
-DartifactId=test \
-Dversion=1.0.0-SNAPSHOT \
-Dpackaging=jar \
-DrepositoryId=nexus-mine \
-Durl=http://localhost:8081/repository/maven-snapshots/ \
-s ./settings.xml -gs ./settings.xml \
-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ignore.validity.dates=true \

参数说明：
-Dfile 要上传包的本地路径
-DpomFile 本地 pom 文件路径。如果没有pom文件，maven会让你生成一个简易的pom文件
-DgroupId -DartifactId -Dversion 要上传的坐标名称
-Dpackaging 要上传包的格式。如 jar、rar、war、zip、...
-DrepositoryId 用于查找 settings 中配置的仓库设置，从而找到 server 中要使用的账号、密码
-Durl 要上传的仓库地址
-s ./settings.xml -gs ./settings.xml 本地 settings 文件绝对路径
-Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ignore.validity.dates=true 证书校验（可忽略）
```

## 镜像制作 docker

`docker-maven-plugin` —— 从应用构建到镜像推送

```xml title="pom.xml"
<plugin>
  <groupId>io.fabric8</groupId>
  <artifactId>docker-maven-plugin</artifactId>
  <version>0.44.0</version>
  <configuration>
    <images>
      <image>
        <name>example/${project.name}:${project.version}</name>
        <alias>${project.name}</alias> <!-- 镜像别名，便于引用 -->
        <build>
          <from>openjdk:21</from>
          <!-- 使用 Maven Assembly 插件指定文件集合并到镜像中 -->
          <assembly>
            <!-- artifact 配置后，插件会将 jar 包复制到 maven 目录下 -->
            <descriptorRef>artifact</descriptorRef>
          </assembly>
          <!-- 设置容器启动时执行的命令 -->
          <cmd>java -jar maven/${project.name}-${project.version}.jar</cmd>
        </build>
        <run>
          <containerNamePattern>%a-%i</containerNamePattern> <!-- 容器启动后的命名规则。 %a=alias, %i=容器编号（多容器情况使用） -->
          <ports>
            <port>8000:8000</port>
          </ports>
          <!-- 等待策略，确保容器内服务可用 -->
          <wait>
            <!-- HTTP 检查，等待容器内的 HTTp 服务再指定 URL 上响应 -->
            <http>
              <url>http://localhost:8080</url>
            </http>
            <time>1000</time> <!-- 设置超时时间，单位毫秒 -->
          </wait>
        </run>
      </image>
    </images>
  </configuration>
  <executions>
    <execution>
      <id>build</id>
      <phase>verify</phase>
      <goals>
        <goal>build</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

## 镜像制作 dockerfile

`dockerfile-maven-plugin`

::: tabs

@tab 插件配置

```xml title="pom.xml"
<plugin>
  <groupId>com.spotify</groupId>
  <artifactId>dockerfile-maven-plugin</artifactId>
  <version>1.4.13</version>
  <executions>
    <execution>
      <id>default</id>
      <phase>package</phase>
      <goals>
        <goal>build</goal>
      </goals>
    </execution>
  </executions>
  <configuration>
    <repository>example/${project.artifactId}</repository>
    <tag>${project.version}</tag>
    <buildArgs>
      <JAR_FILE>${project.build.finalName}.jar</JAR_FILE>
    </buildArgs>
  </configuration>
</plugin>
```

@tab 构建配置

```dockerfile title="Dockerfile"
FROM openjdk:21
ARG JAR_FILE
ADD target/${JAR_FILE} /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
```

:::

## 镜像制作 jib

`jib-maven-plugin` —— Google 开源的 maven 项目 docker 镜像构建插件

```xml
<plugin>
  <groupId>com.google.cloud.tools</groupId>
  <artifactId>jib-maven-plugin</artifactId>
  <version>0.9.6</version>
  <configuration>
    <from>
      <image>openjdk:alpine</image>
    </from>
    <to>
      <image>registry.hub.docker.com/example/maven-docker-demo</image>
    </to>
  </configuration>
</plugin>
```

```bash
# 构建镜像
mvn compile jib:dockerBuild
# 构建镜像并推送
mvn compile jib:build
```
