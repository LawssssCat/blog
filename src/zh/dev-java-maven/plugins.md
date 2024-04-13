---
title: Maven 插件
date: 2024-04-13
tags:
  - java
  - maven
order: 3
---

通过 Maven 插件方式，Maven 可以扩展构建功能。
甚至能管理 C\#、Ruby、Scala 和其他语言编写的项目，如 `scala-maven-plugin`、`dotnet-maven-plugin`。

<!-- more -->

## 插件

todo https://www.bilibili.com/video/BV1au4y1e7rE

前提：

- 可以在 maven 生命周期（lifecycle）的每个阶段（phase）上绑定多个插件（plugin）的目标（goal）。当 maven 执行到对应的阶段时就会执行这些插件的目标。
- 多个目标（goal）绑定同一个阶段（phase）时，maven 会从上到下依次执行。
- 一个插件（plugin）可以包含多个目标（goal）。

通过插件，可以在 maven 生命周期的某个阶段做特定的事情，比如：打包源码、打包 fat jar、混淆、加密、重新打包等。

### 插件命名规则

一般定义插件的 artifactId 为 `xxx-maven-plugin` 或者 `maven-xxx-plugin`。
这里的 xxx 会被识别为前缀（prefix）。

```bash
# gav:goal
mvn com.roadjava.clazz:encrypt-maven-plugin:1.0.0:encrypt
# prefix:goal
mvn encrypt:encrypt
```

上面两句是等价的，就是调用 encrypt 插件的 encrypt 目标的 `execute()` 方法。

### 常用插件

#### deploy

```bash
[INFO] --- deploy:2.8.2:help (default-cli) @ demo-java ---
[INFO] Apache Maven Deploy Plugin 2.8.2
  Uploads the project artifacts to the internal remote repository.
```

##### 区别 deploy 和 deploy-file

- `deploy:deploy` 用于自动安装工件、其 POM 和特定项目生成的附加工件。与部署相关的大多数（如果不是全部）信息都存储在项目的 pom 中。
- `deploy:deploy-file` 用于安装单个工件及其 pom。

::: details

deploy:deploy

```bash
deploy:deploy
  Deploys an artifact to remote repository.

  Available parameters:

    altDeploymentRepository
      Specifies an alternative repository to which the project artifacts should
      be deployed ( other than those specified in <distributionManagement> ).
      Format: id::layout::url
      id
        The id can be used to pick up the correct credentials from the
        settings.xml
      layout
        Either default for the Maven2 layout or legacy for the Maven1 layout.
        Maven3 also uses the default layout.
      url
        The location of the repository

    altReleaseDeploymentRepository
      The alternative repository to use when the project has a final version.

    altSnapshotDeploymentRepository
      The alternative repository to use when the project has a snapshot version.

    deployAtEnd
      Whether every project should be deployed during its own deploy-phase or at
      the end of the multimodule build. If set to true and the build fails, none
      of the reactor projects is deployed. (experimental)

    retryFailedDeploymentCount
      Parameter used to control how many times a failed deployment will be
      retried before giving up and failing. If a value outside the range 1-10 is
      specified it will be pulled to the nearest value within the range 1-10.

    skip
      Set this to 'true' to bypass artifact deploy

    updateReleaseInfo
      Parameter used to update the metadata to make the artifact as release.
```

deploy:deploy-file

```bash
deploy:deploy-file
  Installs the artifact in the remote repository.

  Available parameters:

    artifactId
      ArtifactId of the artifact to be deployed. Retrieved from POM file if
      specified.

    classifier
      Add classifier to the artifact

    classifiers
      A comma separated list of classifiers for each of the extra side artifacts
      to deploy. If there is a mis-match in the number of entries in files or
      types, then an error will be raised.

    description
      Description passed to a generated POM file (in case of generatePom=true)

    file
      File to be deployed.
      Required: Yes

    files
      A comma separated list of files for each of the extra side artifacts to
      deploy. If there is a mis-match in the number of entries in types or
      classifiers, then an error will be raised.

    generatePom
      Upload a POM for this artifact. Will generate a default POM if none is
      supplied with the pomFile argument.

    groupId
      GroupId of the artifact to be deployed. Retrieved from POM file if
      specified.

    javadoc
      The bundled API docs for the artifact.

    packaging
      Type of the artifact to be deployed. Retrieved from the <packaging>
      element of the POM file if a POM file specified. Defaults to the file
      extension if it is not specified via command line or POM.
      Maven uses two terms to refer to this datum: the <packaging> element for
      the entire POM, and the <type> element in a dependency specification.

    pomFile
      Location of an existing POM file to be deployed alongside the main
      artifact, given by the ${file} parameter.

    repositoryId
      Server Id to map on the <id> under <server> section of settings.xml In
      most cases, this parameter will be required for authentication.
      Required: Yes

    repositoryLayout
      The type of remote repository layout to deploy to. Try legacy for a Maven
      1.x-style repository layout.

    retryFailedDeploymentCount
      Parameter used to control how many times a failed deployment will be
      retried before giving up and failing. If a value outside the range 1-10 is
      specified it will be pulled to the nearest value within the range 1-10.

    sources
      The bundled sources for the artifact.

    types
      A comma separated list of types for each of the extra side artifacts to
      deploy. If there is a mis-match in the number of entries in files or
      classifiers, then an error will be raised.

    uniqueVersion
      Whether to deploy snapshots with a unique version or not.

    updateReleaseInfo
      Parameter used to update the metadata to make the artifact as release.

    url
      URL where the artifact will be deployed.
      ie ( file:///C:/m2-repo or scp://host.com/path/to/repo )
      Required: Yes

    version
      Version of the artifact to be deployed. Retrieved from POM file if
      specified.
```

:::

##### 使用 deploy 目标

settings.xml 设置私服位置

```xml
<mirror>
  <id>nexus-mine</id>
  <mirrorOf>central</mirrorOf>
  <name>Nexus mine</name>
  <url>http://localhost:8081/repository/maven-public/</url>
</mirror>
```

settings.xml 设置私服认证信息

```xml
<server>
  <id>nexus-mine</id>
  <username>admin</username>
  <password>xxx</password>
</server>
```

pom.xml 设置部署位置

```xml
<distributionManagement>
  <snapshotRepository>
    <!-- id 需要与 settings.xml 上一致 -->
    <id>nexus-mine</id>
    <url>http://localhost:8081/repository/maven-snapshots/</url>
  </snapshotRepository>
</distributionManagement>
```

pom.xml 设置坐标。

```xml
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

上传命令

```bash
mvn clean deploy -s ./settings.xml -gs ./settings.xml

# -s,--settings <arg> —— Alternate path for the user settings file
# -gs,--global-settings <arg> —— Alternate path for the global settings file
```

##### 使用 deploy 目标的密码加密方案

上传私服需要设置明文密码，这一般是不被允许的。

```xml
<server>
  <id>nexus-mine</id>
  <username>admin</username>
  <password>xxx</password>
</server>
```

加密方案：

- 配置环境变量
- https://maven.apache.org/guides/mini/guide-encryption.html

todo

##### 使用 deploy-file 目标

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

### 自定义

编写 pom.xml 文件

```xml
<!-- 1. 指定 packaging 打包方式 -->
<packaging>maven-plugin</packaging>
<artifactId>custom-plugin</artifactId>
<dependencies>
  <!-- 2. 引入 api 依赖 -->
  <dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-plugin-api</artifactId>
    <version>3.5.2</version>
  </dependency>
</dependencies>
```

使用注释或者注解的方式进行开发

#### 使用注释开发插件（不推荐）

```java
/**
 * mojo: maven old java abject，每个 mojo 类都是 maven plugin 的一个执行目标（goal）。
 *
 * @goal comment
 */
public class CommentMojo extends AbstractMojo {
  @Override
  public void execute() throws MojoExecutionException, MojoFailureExecution {
    Log log = getLog();
    log.info("执行了 goal:comment 的 execute 方法");
  }
}
```

todo

## 场景

### 语法兼容

todo `maven-compiler-plugin`

### 静态检查

静态检查希望对代码库或二进制文件在不运行任何相关代码的情况下进行测试，以发现潜在的问题、确保代码质量。

- CheckStyle —— 代码规范检查工具
- PMD —— 代码编码缺陷检查工具，如：未使用的变量、空 try\-catch 块、不必要的对象创建
- Findbugs —— 将编译后的字节码与一组错误模型对比，识别出代码中的缺陷。如：未使用的错误方法、空指针引用、资源泄露等

todo
